package it.gov.pagopa.pu.processexecutions.enums.ingestion.version;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum DpInstallmentsIngestionFlowFileVersion implements VersionEnum {
  V1_0("1.0"),
  V1_1("1.1"),
  V1_2("1.2"),
  V1_3("1.3"),
  V1_4("1.4"),
  V2_0("2.0");

  private final String value;

  DpInstallmentsIngestionFlowFileVersion(String value) {
    this.value = value;
  }

  @JsonCreator
  public static DpInstallmentsIngestionFlowFileVersion forValue(String value) {
    return VersionEnumUtils.fromValue(DpInstallmentsIngestionFlowFileVersion.class, value);
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}

