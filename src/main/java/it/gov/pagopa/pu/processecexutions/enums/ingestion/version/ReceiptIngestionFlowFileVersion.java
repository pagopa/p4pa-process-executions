package it.gov.pagopa.pu.processecexutions.enums.ingestion.version;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum ReceiptIngestionFlowFileVersion implements VersionEnum {
  V1_0("1.0"),
  V1_1("1.1"),
  V1_2("1.2"),
  V1_3("1.3");

  private final String value;

  ReceiptIngestionFlowFileVersion(String value) {
    this.value = value;
  }

  @JsonCreator
  public static ReceiptIngestionFlowFileVersion forValue(String value) {
    return VersionEnumUtils.fromValue(ReceiptIngestionFlowFileVersion.class, value);
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
