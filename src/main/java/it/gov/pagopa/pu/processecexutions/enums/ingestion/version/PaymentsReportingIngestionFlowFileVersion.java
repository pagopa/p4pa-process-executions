package it.gov.pagopa.pu.processecexutions.enums.ingestion.version;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum PaymentsReportingIngestionFlowFileVersion implements VersionEnum {
  V1_0("1.0");

  private final String value;

  PaymentsReportingIngestionFlowFileVersion(String value) {
    this.value = value;
  }

  @JsonCreator
  public static PaymentsReportingIngestionFlowFileVersion forValue(String value) {
    return VersionEnumUtils.fromValue(PaymentsReportingIngestionFlowFileVersion.class, value);
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
