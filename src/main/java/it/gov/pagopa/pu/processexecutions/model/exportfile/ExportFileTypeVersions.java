package it.gov.pagopa.pu.processexecutions.model.exportfile;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(oneOf = {
  ClassificationsExportFileVersion.class,
  PaidExportFileVersion.class,
  ReceiptsArchivingExportFileVersion.class,
})
public interface ExportFileTypeVersions {
  String getValue();
}
