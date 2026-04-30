package it.gov.pagopa.pu.processecexutions.connector.workflowhub;

import it.gov.pagopa.pu.processecexutions.connector.workflowhub.client.ExportFileClient;
import it.gov.pagopa.pu.processecexutions.model.ExportFile;
import it.gov.pagopa.pu.workflowhub.dto.generated.WorkflowCreatedDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExportFileServiceImpl implements ExportFileService{

  private final ExportFileClient exportFileClient;

  public ExportFileServiceImpl(ExportFileClient exportFileClient) {
    this.exportFileClient = exportFileClient;
  }

  @Override
  public void invokeExportFileWorkflow(ExportFile<?> exportFile, String accessToken) {
    log.debug("Invoking export file workflow for ExportFileTypeEnum: {} , exportFileId: {}", exportFile.getExportFileType(), exportFile.getExportFileId());
    WorkflowCreatedDTO response = exportFileClient.exportFile(exportFile.getExportFileId(), exportFile.getExportFileType(), accessToken);
    log.info("Invoked workflow having workflowId:{}", response.getWorkflowId());
  }
}
