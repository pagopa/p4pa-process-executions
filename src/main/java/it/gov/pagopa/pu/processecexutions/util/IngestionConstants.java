package it.gov.pagopa.pu.processecexutions.util;

import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileTypeEnum;
import it.gov.pagopa.pu.processecexutions.enums.DpInstallmentsIngestionFlowFileVersion;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class IngestionConstants {
  private IngestionConstants() {
  }

  private static final Map<IngestionFlowFileTypeEnum, List<String>> availableVersions;

  static {
    availableVersions = Map.of(
      IngestionFlowFileTypeEnum.DP_INSTALLMENTS, Arrays.stream(DpInstallmentsIngestionFlowFileVersion.values()).map(DpInstallmentsIngestionFlowFileVersion::getValue).toList()
    );
  }

  public static List<String> getAvailableVersions(IngestionFlowFileTypeEnum fileType) {
    return availableVersions.get(fileType);
  }
}
