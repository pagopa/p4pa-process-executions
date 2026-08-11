package it.gov.pagopa.pu.processexecutions.connector.workflowhub;

import it.gov.pagopa.pu.processexecutions.model.ExportFile;

public interface ExportFileService {
  void invokeExportFileWorkflow(ExportFile<?> exportFile, String accessToken);
}
