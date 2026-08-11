package it.gov.pagopa.pu.processexecutions.model.exportfile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum ClassificationsExportFileVersion implements ExportFileTypeVersions {

  V1_3("v1.3"),
  V1_4("v1.4");

  final String value;

  ClassificationsExportFileVersion(String value){
    this.value = value;
  }

  @JsonCreator
  public static ClassificationsExportFileVersion forValue(String value){
    return ClassificationsExportFileVersion.valueOf(value);
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return getValue();
  }
}
