package it.gov.pagopa.pu.processecexutions.model.exportfile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum PaidExportFileVersion {
  V1("v1.0"),
  V1_1("v1.1"),
  V1_2("v1.2"),
  V1_3("v1.3");

  final String value;

  PaidExportFileVersion(String value){
    this.value = value;
  }

  @JsonCreator
  public static PaidExportFileVersion forValue(String value){
    return PaidExportFileVersion.valueOf(value);
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
