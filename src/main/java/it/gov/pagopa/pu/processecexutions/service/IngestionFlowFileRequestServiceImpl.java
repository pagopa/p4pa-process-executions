package it.gov.pagopa.pu.processecexutions.service;

import it.gov.pagopa.pu.processecexutions.connector.workflowhub.IngestionFlowFileService;
import it.gov.pagopa.pu.processecexutions.dto.generated.IngestionFlowFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileStatus;
import it.gov.pagopa.pu.processecexutions.mapper.IngestionFlowFileRequestMapper;
import it.gov.pagopa.pu.processecexutions.model.IngestionFlowFile;
import it.gov.pagopa.pu.processecexutions.repository.IngestionFlowFileRepository;
import org.springframework.stereotype.Service;

@Service
public class IngestionFlowFileRequestServiceImpl implements IngestionFlowFileRequestService {

  private final IngestionFlowFileRequestMapper requestMapper;
  private final IngestionFlowFileRepository repository;
  private final IngestionFlowFileService workflowInvokerService;

  public IngestionFlowFileRequestServiceImpl(IngestionFlowFileRequestMapper requestMapper, IngestionFlowFileRepository repository, IngestionFlowFileService workflowInvokerService) {
    this.requestMapper = requestMapper;
    this.repository = repository;
    this.workflowInvokerService = workflowInvokerService;
  }

  @Override
  public IngestionFlowFile handleUploaded(IngestionFlowFileRequestDTO requestDTO, String operatorExternalId,
    String accessToken) {
    IngestionFlowFile saved = repository.save(requestMapper.map(requestDTO, operatorExternalId, IngestionFlowFileStatus.UPLOADED));
    try{
      workflowInvokerService.invokeIngestionWorkflow(saved, accessToken);
    } catch (UnsupportedOperationException e){
      saved.setStatus(IngestionFlowFileStatus.ERROR);
      saved.setErrorDescription("Flow type not supported");
      repository.save(saved);
    } catch (RuntimeException e){
      saved.setStatus(IngestionFlowFileStatus.ERROR);
      saved.setErrorDescription(e.getMessage());
      repository.save(saved);
      throw e;
    }
    return saved;
  }

  @Override
  public IngestionFlowFile handleReservation(IngestionFlowFileRequestDTO requestDTO, String operatorExternalId) {
    return repository.save(requestMapper.map(requestDTO, operatorExternalId, IngestionFlowFileStatus.WAITING_FILE));
  }
}
