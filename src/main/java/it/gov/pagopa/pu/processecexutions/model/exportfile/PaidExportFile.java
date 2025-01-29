package it.gov.pagopa.pu.processecexutions.model.exportfile;

import io.swagger.v3.oas.annotations.media.Schema;
import it.gov.pagopa.pu.processecexutions.enums.ExportFlowFileType;
import it.gov.pagopa.pu.processecexutions.model.ExportFile;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("PAID")
public class PaidExportFile extends ExportFile<PaidExportFileFilter> {

  @Schema(type = "string", allowableValues = "PAID")
  @Override
  public ExportFlowFileType getFlowFileType() {
    return super.getFlowFileType();
  }

  @JdbcTypeCode(SqlTypes.JSON)
  private PaidExportFileFilter filterFields;
}
