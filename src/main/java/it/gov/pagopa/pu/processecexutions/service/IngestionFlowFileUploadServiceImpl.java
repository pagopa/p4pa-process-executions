package it.gov.pagopa.pu.processecexutions.service;

import it.gov.pagopa.pu.processecexutions.connector.workflowhub.IngestionFlowFileService;
import it.gov.pagopa.pu.processecexutions.dto.generated.IngestionFlowFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileStatus;
import it.gov.pagopa.pu.processecexutions.mapper.IngestionFlowFileRequestMapper;
import it.gov.pagopa.pu.processecexutions.model.IngestionFlowFile;
import it.gov.pagopa.pu.processecexutions.repository.IngestionFlowFileRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class IngestionFlowFileUploadServiceImpl implements IngestionFlowFileUploadService {

  private final IngestionFlowFileRequestMapper uploadedRequestMapper;
  private final IngestionFlowFileRepository repository;
  private final IngestionFlowFileService workflowInvokerService;

  public IngestionFlowFileUploadServiceImpl(IngestionFlowFileRequestMapper uploadedRequestMapper, IngestionFlowFileRepository repository, IngestionFlowFileService workflowInvokerService) {
    this.uploadedRequestMapper = uploadedRequestMapper;
    this.repository = repository;
    this.workflowInvokerService = workflowInvokerService;
  }

  @Override
  @Transactional
  public IngestionFlowFile handleUploaded(IngestionFlowFileRequestDTO requestDTO, String operatorExternalId,
    String accessToken) {
    IngestionFlowFile saved = repository.save(uploadedRequestMapper.map(requestDTO, operatorExternalId));
    try{
      workflowInvokerService.invokeIngestionWorkflow(saved, accessToken);
    } catch (UnsupportedOperationException e){
      saved.setStatus(IngestionFlowFileStatus.ERROR);
      saved.setErrorDescription("Flow type not supported");
      repository.save(saved);
    }
    return saved;
  }
}
