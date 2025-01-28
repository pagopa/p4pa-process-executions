package it.gov.pagopa.pu.processecexutions.model.exportfile;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "_type")
@JsonSubTypes({
  @JsonSubTypes.Type(name = "CLASSIFICATIONS", value = ClassificationExportFileFilter.class)
})
public interface ExportFileFilter extends Serializable {
  String get_type();
}
