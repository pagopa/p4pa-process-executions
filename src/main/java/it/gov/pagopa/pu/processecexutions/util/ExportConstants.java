package it.gov.pagopa.pu.processecexutions.util;

import it.gov.pagopa.pu.processecexutions.enums.ExportFileTypeEnum;
import it.gov.pagopa.pu.processecexutions.model.exportfile.ClassificationsExportFileVersion;
import it.gov.pagopa.pu.processecexutions.model.exportfile.ReceiptsArchivingExportFileVersion;
import it.gov.pagopa.pu.processecexutions.model.exportfile.PaidExportFileVersion;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ExportConstants {
  private ExportConstants() {}

  private static final Map<ExportFileTypeEnum, List<String>> availableVersions;

  static {
    availableVersions = Map.of(
      ExportFileTypeEnum.PAID, Arrays.stream(PaidExportFileVersion.values()).map(PaidExportFileVersion::getValue).toList(),
      ExportFileTypeEnum.RECEIPTS_ARCHIVING, Arrays.stream(ReceiptsArchivingExportFileVersion.values()).map(ReceiptsArchivingExportFileVersion::getValue).toList(),
      ExportFileTypeEnum.CLASSIFICATIONS, Arrays.stream(ClassificationsExportFileVersion.values()).map(ClassificationsExportFileVersion::getValue).toList()
    );
  }

  public static List<String> getAvailableVersions(ExportFileTypeEnum fileType) {
    return availableVersions.get(fileType);
  }
}
