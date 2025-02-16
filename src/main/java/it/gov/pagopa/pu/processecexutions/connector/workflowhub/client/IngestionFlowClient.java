package it.gov.pagopa.pu.processecexutions.connector.workflowhub.client;

import it.gov.pagopa.pu.processecexutions.connector.workflowhub.config.WorkflowHubApisHolder;
import it.gov.pagopa.pu.workflowhub.dto.generated.WorkflowCreatedDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IngestionFlowClient {

  private final WorkflowHubApisHolder workflowHubApisHolder;

  public IngestionFlowClient(WorkflowHubApisHolder workflowHubApisHolder) {
    this.workflowHubApisHolder = workflowHubApisHolder;
  }

  public WorkflowCreatedDTO ingestPaymentsReportingFile(Long ingestionFlowFileId, String accessToken) {
    return workflowHubApisHolder.getIngestionFlowApi(accessToken)
      .ingestPaymentsReportingFile(ingestionFlowFileId);
  }

  public WorkflowCreatedDTO ingestTreasuryOpi(Long ingestionFlowFileId, String accessToken) {
    return workflowHubApisHolder.getIngestionFlowApi(accessToken)
      .ingestTreasuryOpi(ingestionFlowFileId);
  }

}
