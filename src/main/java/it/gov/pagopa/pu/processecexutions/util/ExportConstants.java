package it.gov.pagopa.pu.processecexutions.util;

import it.gov.pagopa.pu.processecexutions.enums.ExportFileType;
import it.gov.pagopa.pu.processecexutions.model.exportfile.ArchivingExportFileVersion;
import it.gov.pagopa.pu.processecexutions.model.exportfile.PaidExportFileVersion;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ExportConstants {
  private ExportConstants() {}

  private static final Map<ExportFileType, List<String>> availableVersions;

  static {
    availableVersions = Map.of(
      ExportFileType.PAID, Arrays.stream(PaidExportFileVersion.values()).map(PaidExportFileVersion::getValue).toList(),
      ExportFileType.ARCHIVING, Arrays.stream(ArchivingExportFileVersion.values()).map(ArchivingExportFileVersion::getValue).toList()
    );
  }

  public static List<String> getAvailableVersions(ExportFileType fileType) {
    return availableVersions.get(fileType);
  }
}
