package it.gov.pagopa.pu.processexecutions.enums.ingestion.version;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum TreasuryCsvCompletengestionFlowFileVersion implements VersionEnum {
  V1_0("1.0");

  private final String value;

  TreasuryCsvCompletengestionFlowFileVersion(String value) {
    this.value = value;
  }

  @JsonCreator
  public static TreasuryCsvCompletengestionFlowFileVersion forValue(String value) {
    return VersionEnumUtils.fromValue(TreasuryCsvCompletengestionFlowFileVersion.class, value);
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
