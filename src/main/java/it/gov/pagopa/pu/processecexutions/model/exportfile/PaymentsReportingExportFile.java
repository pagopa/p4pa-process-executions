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
@DiscriminatorValue("PAYMENTS_REPORTING")
public class PaymentsReportingExportFile extends ExportFile<PaymentsReportingExportFileFilter> {

  @Schema(type = "string", allowableValues = "PAYMENTS_REPORTING")
  @Override
  public ExportFlowFileType getFlowFileType() {
    return super.getFlowFileType();
  }

  @JdbcTypeCode(SqlTypes.JSON)
  private PaymentsReportingExportFileFilter filterFields;
}
