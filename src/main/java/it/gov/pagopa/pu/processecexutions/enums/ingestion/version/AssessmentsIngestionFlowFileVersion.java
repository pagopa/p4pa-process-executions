package it.gov.pagopa.pu.processecexutions.enums.ingestion.version;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum AssessmentsIngestionFlowFileVersion implements VersionEnum {
  V1_0("1.0");

  private final String value;

  AssessmentsIngestionFlowFileVersion(String value) {
    this.value = value;
  }

  @JsonCreator
  public static AssessmentsIngestionFlowFileVersion forValue(String value) {
    return VersionEnumUtils.fromValue(AssessmentsIngestionFlowFileVersion.class, value);
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
