package it.gov.pagopa.pu.processecexutions.model.exportfile;

import io.swagger.v3.oas.annotations.media.Schema;
import it.gov.pagopa.pu.processecexutions.enums.ExportFlowFileType;
import it.gov.pagopa.pu.processecexutions.model.ExportFile;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("CLASSIFICATIONS")
public class ClassificationsExportFile extends ExportFile<ClassificationsExportFileFilter> {

  @Schema(type = "string", allowableValues = "CLASSIFICATIONS")
  @Override
  public ExportFlowFileType getFlowFileType() {
    return super.getFlowFileType();
  }

  @JdbcTypeCode(SqlTypes.JSON)
  private ClassificationsExportFileFilter filterFields;
}
