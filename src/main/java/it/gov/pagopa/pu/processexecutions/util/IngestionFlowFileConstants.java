package it.gov.pagopa.pu.processexecutions.util;

import it.gov.pagopa.pu.processexecutions.enums.IngestionFlowFileTypeEnum;
import it.gov.pagopa.pu.processexecutions.enums.ingestion.version.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class IngestionFlowFileConstants {
  private IngestionFlowFileConstants() {
  }

  private static final Map<IngestionFlowFileTypeEnum, List<String>> availableVersions;

  static {
    availableVersions = Map.ofEntries(
      entry(IngestionFlowFileTypeEnum.DP_INSTALLMENTS, extractVersions(DpInstallmentsIngestionFlowFileVersion.values())),
      entry(IngestionFlowFileTypeEnum.RECEIPT, extractVersions(ReceiptIngestionFlowFileVersion.values())),
      entry(IngestionFlowFileTypeEnum.TREASURY_OPI, extractVersions(TreasuryOpiIngestionFlowFileVersion.values())),
      entry(IngestionFlowFileTypeEnum.TREASURY_POSTE, extractVersions(TreasuryPosteIngestionFlowFileVersion.values())),
      entry(IngestionFlowFileTypeEnum.TREASURY_CSV, extractVersions(TreasuryCsvIngestionFlowFileVersion.values())),
      entry(IngestionFlowFileTypeEnum.TREASURY_CSV_COMPLETE, extractVersions(TreasuryCsvCompletengestionFlowFileVersion.values())),
      entry(IngestionFlowFileTypeEnum.TREASURY_XLS, extractVersions(TreasuryCsvCompletengestionFlowFileVersion.values())),
      entry(IngestionFlowFileTypeEnum.PAYMENT_NOTIFICATION, extractVersions(PaymentNotificationIngestionFlowFileVersion.values())),
      entry(IngestionFlowFileTypeEnum.ORGANIZATIONS, extractVersions(OrganizationIngestionFlowFileVersion.values())),
      entry(IngestionFlowFileTypeEnum.DEBT_POSITIONS_TYPE, extractVersions(DebtPositionTypeIngestionFlowFileVersion.values())),
      entry(IngestionFlowFileTypeEnum.DEBT_POSITIONS_TYPE_ORG, extractVersions(DebtPositionTypeOrgIngestionFlowFileVersion.values())),
      entry(IngestionFlowFileTypeEnum.SEND_NOTIFICATION, extractVersions(SendNotificationIngestionFlowFileVersion.values())),
      entry(IngestionFlowFileTypeEnum.ASSESSMENTS_REGISTRY, extractVersions(AssessmentsRegistryIngestionFlowFileVersion.values())),
      entry(IngestionFlowFileTypeEnum.ASSESSMENTS, extractVersions(AssessmentsIngestionFlowFileVersion.values())),
      entry(IngestionFlowFileTypeEnum.ORGANIZATIONS_SIL_SERVICE, extractVersions(OrgSilIngestionFlowFileVersion.values()))
    );
  }

  private static <T extends Enum<T> & VersionEnum> List<String> extractVersions(T[] values) {
    return Arrays.stream(values)
      .map(VersionEnum::getValue)
      .toList();
  }

  public static List<String> getAvailableVersions(IngestionFlowFileTypeEnum fileType) {
    return availableVersions.get(fileType);
  }
}
