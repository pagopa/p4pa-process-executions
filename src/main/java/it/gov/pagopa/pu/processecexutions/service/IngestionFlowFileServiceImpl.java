package it.gov.pagopa.pu.processecexutions.service;

import it.gov.pagopa.pu.processecexutions.dto.generated.IngestionFlowFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.mapper.IngestionFlowFileRequestMapper;
import it.gov.pagopa.pu.processecexutions.model.IngestionFlowFile;
import it.gov.pagopa.pu.processecexutions.repository.IngestionFlowFileRepository;
import org.springframework.stereotype.Service;

@Service
public class IngestionFlowFileServiceImpl implements IngestionFlowFileService{

  private final IngestionFlowFileRequestMapper uploadedRequestMapper;
  private final IngestionFlowFileRepository repository;
  private final IngestionFlowFileWorkflowInvokerService workflowInvokerService;

  public IngestionFlowFileServiceImpl(IngestionFlowFileRequestMapper uploadedRequestMapper, IngestionFlowFileRepository repository, IngestionFlowFileWorkflowInvokerService workflowInvokerService) {
    this.uploadedRequestMapper = uploadedRequestMapper;
    this.repository = repository;
    this.workflowInvokerService = workflowInvokerService;
  }

  @Override
  public IngestionFlowFile handleUploaded(IngestionFlowFileRequestDTO requestDTO) {
    IngestionFlowFile saved = repository.save(uploadedRequestMapper.map(requestDTO));
    workflowInvokerService.invokeIngestionWorkflow(saved);
    return saved;
  }
}
