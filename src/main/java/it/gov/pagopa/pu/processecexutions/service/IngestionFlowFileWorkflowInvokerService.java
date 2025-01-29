package it.gov.pagopa.pu.processecexutions.service;

import it.gov.pagopa.pu.processecexutions.connector.workflowhub.client.IngestionFlowClient;
import it.gov.pagopa.pu.processecexutions.model.IngestionFlowFile;
import it.gov.pagopa.pu.workflowhub.dto.generated.WorkflowCreatedDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IngestionFlowFileWorkflowInvokerService {
  private final IngestionFlowClient ingestionFlowClient;

  public IngestionFlowFileWorkflowInvokerService(
    IngestionFlowClient ingestionFlowClient) {
    this.ingestionFlowClient = ingestionFlowClient;
  }

  void invokeIngestionWorkflow(IngestionFlowFile ingestionFlowFile, String accessToken){
    if(ingestionFlowFile!=null){
      WorkflowCreatedDTO response = switch(ingestionFlowFile.getFlowFileType()){
        case PAYMENTS_REPORTING -> ingestionFlowClient.ingestPaymentsReportingFile(ingestionFlowFile.getIngestionFlowFileId(), accessToken);
        case TREASURY_OPI -> ingestionFlowClient.ingestTreasuryOpi(ingestionFlowFile.getIngestionFlowFileId(), accessToken);
        default -> throw new UnsupportedOperationException("Unsupported ingestionFlowFileType");
      };
      if(response!=null){
        log.info("Invoked workflow having workflowId:{}", response.getWorkflowId());
      }
    }
  }

}
