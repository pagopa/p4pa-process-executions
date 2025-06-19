package it.gov.pagopa.pu.processecexutions.model.exportfile;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum ReceiptsArchivingExportFileVersion implements ExportFileTypeVersions {
  V1("v1.0");

  final String value;

  ReceiptsArchivingExportFileVersion(String value){
    this.value = value;
  }

  @JsonCreator
  public static ReceiptsArchivingExportFileVersion forValue(String value){
    return  ReceiptsArchivingExportFileVersion.valueOf(value);
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
