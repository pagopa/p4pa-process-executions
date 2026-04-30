package it.gov.pagopa.pu.processecexutions.model.exportfile;

import io.swagger.v3.oas.annotations.media.Schema;
import it.gov.pagopa.pu.processecexutions.enums.ExportFileTypeEnum;
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
@DiscriminatorValue("PAYMENTS_REPORTING")
public class PaymentsReportingExportFile extends ExportFile<PaymentsReportingExportFileFilter> {

  public PaymentsReportingExportFile(){
    setExportFileType(ExportFileTypeEnum.PAYMENTS_REPORTING);
  }

  @Schema(type = "string", allowableValues = "PAYMENTS_REPORTING")
  @Override
  public ExportFileTypeEnum getExportFileType() {
    return super.getExportFileType();
  }

  @JdbcTypeCode(SqlTypes.JSON)
  private PaymentsReportingExportFileFilter filterFields;
}
