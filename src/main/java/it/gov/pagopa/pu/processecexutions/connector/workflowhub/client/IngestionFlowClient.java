package it.gov.pagopa.pu.processecexutions.connector.workflowhub.client;

import it.gov.pagopa.pu.processecexutions.connector.workflowhub.config.WorkflowHubApisHolder;
import it.gov.pagopa.pu.workflowhub.dto.generated.WorkflowCreatedDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@Slf4j
public class IngestionFlowClient {

    private final WorkflowHubApisHolder workflowHubApisHolder;

    public IngestionFlowClient(WorkflowHubApisHolder workflowHubApisHolder) {
        this.workflowHubApisHolder = workflowHubApisHolder;
    }

  public WorkflowCreatedDTO ingestPaymentsReportingFile(Long ingestionFlowFileId, String accessToken) {
    try {
      return workflowHubApisHolder.getIngestionFlowApi(accessToken)
        .ingestPaymentsReportingFile(ingestionFlowFileId);
    } catch (HttpClientErrorException e) {
      log.error("Error invoking workflow ingestPaymentsReportingFile by ingestionFlowfileId: {}", ingestionFlowFileId, e);
      throw e;
    } catch (Exception e) {
      log.error("Unexpected error while invoking workflow ingestPaymentsReportingFile by ingestionFlowfileId: {}", ingestionFlowFileId, e);
      throw e;
    }
  }

  public WorkflowCreatedDTO ingestTreasuryOpi(Long ingestionFlowFileId, String accessToken) {
    try {
      return workflowHubApisHolder.getIngestionFlowApi(accessToken)
        .ingestTreasuryOpi(ingestionFlowFileId);
    } catch (HttpClientErrorException e) {
      log.error("Error invoking workflow ingestTreasuryOpi by ingestionFlowfileId: {}", ingestionFlowFileId, e);
      throw e;
    } catch (Exception e) {
      log.error("Unexpected error while invoking workflow ingestTreasuryOpi by ingestionFlowfileId: {}", ingestionFlowFileId, e);
      throw e;
    }
  }

}
