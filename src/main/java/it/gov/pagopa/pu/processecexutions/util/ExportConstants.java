package it.gov.pagopa.pu.processecexutions.util;

import it.gov.pagopa.pu.processecexutions.dto.generated.PaidExportFileVersion;
import it.gov.pagopa.pu.processecexutions.enums.ExportFlowFileType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ExportConstants {
  private ExportConstants() {}

  private static final Map<ExportFlowFileType, List<String>> availableVersions;

  static {
    availableVersions = Map.of(
      ExportFlowFileType.PAID, Arrays.stream(PaidExportFileVersion.values()).map(PaidExportFileVersion::getValue).toList()
    );
  }

  public static List<String> getAvailableVersions(ExportFlowFileType fileType) {
    return availableVersions.get(fileType);
  }
}
