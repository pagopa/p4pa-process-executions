package it.gov.pagopa.pu.processecexutions.connector.workflowhub.client;

import it.gov.pagopa.pu.processecexutions.connector.workflowhub.config.WorkflowHubApisHolder;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileTypeEnum;
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

  public WorkflowCreatedDTO ingestFlowFile(Long ingestionFlowFileId, IngestionFlowFileTypeEnum ingestionFlowFileType, String accessToken) {
    try{
      return workflowHubApisHolder.getIngestionFlowApi(accessToken)
        .ingestFlowFile(ingestionFlowFileId, IngestionFlowFileTypeEnum.valueOf(ingestionFlowFileType.name()));
    } catch (HttpClientErrorException.BadRequest e){
      if(e.getResponseBodyAsString().contains("WORKFLOW_INGESTION_FLOW_FILE_NOT_SUPPORTED")){
        throw new UnsupportedOperationException("IngestionFlowFile " + ingestionFlowFileType + " not supported!");
      }
      throw e;
    }
  }

}
