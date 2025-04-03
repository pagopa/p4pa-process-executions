package it.gov.pagopa.pu.processecexutions.connector.workflowhub;

import it.gov.pagopa.pu.processecexutions.model.ExportFile;

public interface ExportFileService {
  void invokeExportFileWorkflow(ExportFile<?> exportFile, String accessToken);
}
