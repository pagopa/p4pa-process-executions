package it.gov.pagopa.pu.processexecutions.connector.workflowhub.client;

import it.gov.pagopa.pu.processexecutions.connector.workflowhub.config.WorkflowHubApisHolder;
import it.gov.pagopa.pu.processexecutions.enums.IngestionFlowFileTypeEnum;
import it.gov.pagopa.pu.processexecutions.exception.common.InvalidValueException;
import it.gov.pagopa.pu.processexecutions.exception.common.RestInvokeInvalidValueException;
import it.gov.pagopa.pu.processexecutions.util.ErrorCodeConstants;
import it.gov.pagopa.pu.workflowhub.dto.generated.CategoryEnum;
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

  public WorkflowCreatedDTO ingestFlowFile(Long ingestionFlowFileId, IngestionFlowFileTypeEnum ingestionFlowFileType, String accessToken) {
    try{
      return workflowHubApisHolder.getIngestionFlowApi(accessToken)
        .ingestFlowFile(ingestionFlowFileId, IngestionFlowFileTypeEnum.valueOf(ingestionFlowFileType.name()));
    } catch (RestInvokeInvalidValueException e){
      if(e.getCategory().equals(CategoryEnum.WORKFLOW_INGESTION_FLOW_FILE_NOT_SUPPORTED.getValue())){
        throw new InvalidValueException(ErrorCodeConstants.ERROR_CODE_INVALID_INGESTION_FLOW_FILE_TYPE, "IngestionFlowFile " + ingestionFlowFileType + " not supported!");
      }
      throw e;
    }
  }

}
