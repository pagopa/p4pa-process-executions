package it.gov.pagopa.pu.processexecutions.model.exportfile;

import io.swagger.v3.oas.annotations.media.Schema;
import it.gov.pagopa.pu.processexecutions.enums.ExportFileTypeEnum;
import it.gov.pagopa.pu.processexecutions.model.ExportFile;
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
    setExportFileType(ExportFileTypeEnum.CLASSIFICATIONS);
  }

  @Schema(type = "string", allowableValues = "CLASSIFICATIONS")
  @Override
  public ExportFileTypeEnum getExportFileType() {
    return super.getExportFileType();
  }

  @JdbcTypeCode(SqlTypes.JSON)
  private ClassificationsExportFileFilter filterFields;
}
