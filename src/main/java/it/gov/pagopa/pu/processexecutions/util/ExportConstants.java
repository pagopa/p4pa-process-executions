package it.gov.pagopa.pu.processexecutions.util;

import it.gov.pagopa.pu.processexecutions.enums.ExportFileTypeEnum;
import it.gov.pagopa.pu.processexecutions.model.exportfile.ClassificationsExportFileVersion;
import it.gov.pagopa.pu.processexecutions.model.exportfile.ExportFileTypeVersions;
import it.gov.pagopa.pu.processexecutions.model.exportfile.PaidExportFileVersion;
import it.gov.pagopa.pu.processexecutions.model.exportfile.ReceiptsArchivingExportFileVersion;

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
