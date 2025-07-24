package it.gov.pagopa.pu.processecexutions.util;

import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileTypeEnum;
import it.gov.pagopa.pu.processecexutions.enums.DpInstallmentsIngestionFlowFileVersion;
import it.gov.pagopa.pu.processecexutions.enums.ReceiptIngestionFlowFileVersion;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class IngestionFlowFileConstants {
  private IngestionFlowFileConstants() {
  }

  private static final Map<IngestionFlowFileTypeEnum, List<String>> availableVersions;

  static {
    availableVersions = Map.of(
      IngestionFlowFileTypeEnum.DP_INSTALLMENTS,
      Arrays.stream(DpInstallmentsIngestionFlowFileVersion.values())
        .map(DpInstallmentsIngestionFlowFileVersion::getValue)
        .toList(),
      IngestionFlowFileTypeEnum.RECEIPT,
      Arrays.stream(ReceiptIngestionFlowFileVersion.values())
        .map(ReceiptIngestionFlowFileVersion::getValue)
        .toList()
    );
  }

  public static List<String> getAvailableVersions(IngestionFlowFileTypeEnum fileType) {
    return availableVersions.get(fileType);
  }
}
