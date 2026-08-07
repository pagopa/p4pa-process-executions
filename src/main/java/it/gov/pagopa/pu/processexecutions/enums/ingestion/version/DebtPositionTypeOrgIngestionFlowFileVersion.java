package it.gov.pagopa.pu.processexecutions.enums.ingestion.version;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum DebtPositionTypeOrgIngestionFlowFileVersion implements VersionEnum {
  V1_0("1.0");

  private final String value;

  DebtPositionTypeOrgIngestionFlowFileVersion(String value) {
    this.value = value;
  }

  @JsonCreator
  public static DebtPositionTypeOrgIngestionFlowFileVersion forValue(String value) {
    return VersionEnumUtils.fromValue(DebtPositionTypeOrgIngestionFlowFileVersion.class, value);
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
