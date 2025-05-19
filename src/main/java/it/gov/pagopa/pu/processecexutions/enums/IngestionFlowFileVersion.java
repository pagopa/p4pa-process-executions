package it.gov.pagopa.pu.processecexutions.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

@Schema(enumAsRef = true)
public enum IngestionFlowFileVersion {
  V1_0("1.0"),
  V1_1("1.1"),
  V1_2("1.2"),
  V1_3("1.3"),
  V1_4("1.4"),
  V2_0("2.0");

  private final String value;

  IngestionFlowFileVersion(String value) {
    this.value = value;
  }

  @JsonCreator
  public static IngestionFlowFileVersion forValue(String value) {
    return Arrays.stream(values())
      .filter(v -> v.value.equals(value))
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException("Invalid value: " + value));
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}

