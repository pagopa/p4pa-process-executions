package it.gov.pagopa.pu.processecexutions.connector.workflowhub;

import it.gov.pagopa.pu.processecexutions.connector.workflowhub.client.IngestionFlowClient;
import it.gov.pagopa.pu.processecexutions.model.IngestionFlowFile;
import it.gov.pagopa.pu.workflowhub.dto.generated.WorkflowCreatedDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IngestionFlowFileServiceImpl implements IngestionFlowFileService {
  private final IngestionFlowClient ingestionFlowClient;

  public IngestionFlowFileServiceImpl(
    IngestionFlowClient ingestionFlowClient) {
    this.ingestionFlowClient = ingestionFlowClient;
  }

  @Override
  public void invokeIngestionWorkflow(IngestionFlowFile ingestionFlowFile, String accessToken) {
    log.debug("Invoking ingestion workflow for ingestionFlowFileType: {} , ingestionFlowFileId: {}", ingestionFlowFile.getFlowFileType(), ingestionFlowFile.getIngestionFlowFileId());
    WorkflowCreatedDTO response = ingestionFlowClient.ingestFlowFile(ingestionFlowFile.getIngestionFlowFileId(), ingestionFlowFile.getFlowFileType(), accessToken);
    log.info("Invoked workflow having workflowId:{}", response.getWorkflowId());
  }

}
