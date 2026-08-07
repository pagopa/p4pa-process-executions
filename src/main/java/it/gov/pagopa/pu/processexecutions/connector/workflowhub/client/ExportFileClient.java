package it.gov.pagopa.pu.processexecutions.connector.workflowhub.client;

import it.gov.pagopa.pu.processexecutions.connector.workflowhub.config.WorkflowHubApisHolder;
import it.gov.pagopa.pu.processexecutions.enums.ExportFileTypeEnum;
import it.gov.pagopa.pu.workflowhub.dto.generated.WorkflowCreatedDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExportFileClient {

  private final WorkflowHubApisHolder workflowHubApisHolder;

  public ExportFileClient(WorkflowHubApisHolder workflowHubApisHolder) {
    this.workflowHubApisHolder = workflowHubApisHolder;
  }

  public WorkflowCreatedDTO exportFile(Long exportFileId, ExportFileTypeEnum exportFileTypeEnum, String accessToken) {
    return workflowHubApisHolder.getExportFileApi(accessToken).exportFile(exportFileId, exportFileTypeEnum);
  }
}
