package it.gov.pagopa.pu.processecexutions.model.exportfile;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum ArchivingExportFileVersion {
  V1("v1.0");

  final String value;

  ArchivingExportFileVersion(String value){
    this.value = value;
  }

  @JsonCreator
  public static  ArchivingExportFileVersion forValue(String value){
    return  ArchivingExportFileVersion.valueOf(value);
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
