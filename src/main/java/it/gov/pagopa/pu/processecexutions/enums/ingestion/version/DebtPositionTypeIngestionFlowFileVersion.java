package it.gov.pagopa.pu.processecexutions.enums.ingestion.version;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum DebtPositionTypeIngestionFlowFileVersion implements VersionEnum {
  V1_0("1.0");

  private final String value;

  DebtPositionTypeIngestionFlowFileVersion(String value) {
    this.value = value;
  }

  @JsonCreator
  public static DebtPositionTypeIngestionFlowFileVersion forValue(String value) {
    return VersionEnumUtils.fromValue(DebtPositionTypeIngestionFlowFileVersion.class, value);
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
