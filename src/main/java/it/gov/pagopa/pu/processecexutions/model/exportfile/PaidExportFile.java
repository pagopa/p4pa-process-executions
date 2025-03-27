package it.gov.pagopa.pu.processecexutions.model.exportfile;

import io.swagger.v3.oas.annotations.media.Schema;
import it.gov.pagopa.pu.processecexutions.enums.ExportFileType;
import it.gov.pagopa.pu.processecexutions.model.ExportFile;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("PAID")
public class PaidExportFile extends ExportFile<PaidExportFileFilter> {

  public PaidExportFile(){
    setExportFileType(ExportFileType.PAID);
  }

  @Schema(type = "string", allowableValues = "PAID")
  @Override
  public ExportFileType getExportFileType() {
    return super.getExportFileType();
  }

  @JdbcTypeCode(SqlTypes.JSON)
  private PaidExportFileFilter filterFields;
}
