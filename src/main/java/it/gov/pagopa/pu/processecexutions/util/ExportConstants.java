package it.gov.pagopa.pu.processecexutions.util;

import it.gov.pagopa.pu.processecexutions.enums.ExportFileTypeEnum;
import it.gov.pagopa.pu.processecexutions.model.exportfile.ClassificationsExportFileVersion;
import it.gov.pagopa.pu.processecexutions.model.exportfile.ExportFileTypeVersions;
import it.gov.pagopa.pu.processecexutions.model.exportfile.PaidExportFileVersion;
import it.gov.pagopa.pu.processecexutions.model.exportfile.ReceiptsArchivingExportFileVersion;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ExportConstants {
  private ExportConstants() {}

  private static final Map<ExportFileTypeEnum, List<ExportFileTypeVersions>> availableVersions;

  static {
    availableVersions = Map.of(
      ExportFileTypeEnum.PAID, Arrays.asList(PaidExportFileVersion.values()),
      ExportFileTypeEnum.RECEIPTS_ARCHIVING, Arrays.asList(ReceiptsArchivingExportFileVersion.values()),
      ExportFileTypeEnum.CLASSIFICATIONS, Arrays.asList(ClassificationsExportFileVersion.values())
    );
  }

  public static List<ExportFileTypeVersions> getAvailableVersions(ExportFileTypeEnum fileType) {
    return availableVersions.get(fileType);
  }
}
