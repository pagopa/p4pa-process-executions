package it.gov.pagopa.pu.processecexutions.enums.ingestion.version;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum PaymentNotificationIngestionFlowFileVersion implements VersionEnum {
  V1_0("1.0");

  private final String value;

  PaymentNotificationIngestionFlowFileVersion(String value) {
    this.value = value;
  }

  @JsonCreator
  public static PaymentNotificationIngestionFlowFileVersion forValue(String value) {
    return VersionEnumUtils.fromValue(PaymentNotificationIngestionFlowFileVersion.class, value);
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
