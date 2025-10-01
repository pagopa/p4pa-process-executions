package it.gov.pagopa.pu.processecexutions.enums.ingestion.version;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum AssessmentsRegistryIngestionFlowFileVersion implements VersionEnum {
  V1_0("1.0");

  private final String value;

  AssessmentsRegistryIngestionFlowFileVersion(String value) {
    this.value = value;
  }

  @JsonCreator
  public static AssessmentsRegistryIngestionFlowFileVersion forValue(String value) {
    return VersionEnumUtils.fromValue(AssessmentsRegistryIngestionFlowFileVersion.class, value);
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
