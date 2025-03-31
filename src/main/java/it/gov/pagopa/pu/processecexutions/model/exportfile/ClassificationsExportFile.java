package it.gov.pagopa.pu.processecexutions.model.exportfile;

import io.swagger.v3.oas.annotations.media.Schema;
import it.gov.pagopa.pu.processecexutions.enums.ExportFileType;
import it.gov.pagopa.pu.processecexutions.model.ExportFile;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("CLASSIFICATIONS")
public class ClassificationsExportFile extends ExportFile<ClassificationsExportFileFilter> {

  public ClassificationsExportFile(){
    setExportFileType(ExportFileType.CLASSIFICATIONS);
  }

  @Schema(type = "string", allowableValues = "CLASSIFICATIONS")
  @Override
  public ExportFileType getExportFileType() {
    return super.getExportFileType();
  }

  @JdbcTypeCode(SqlTypes.JSON)
  private ClassificationsExportFileFilter filterFields;
}
