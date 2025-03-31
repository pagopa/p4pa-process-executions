package it.gov.pagopa.pu.processecexutions.model.exportfile;

import it.gov.pagopa.pu.processecexutions.dto.LocalDateIntervalFilter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassificationsExportFileFilter implements ExportFileFilter {
  private String iuf;
  private String iuv;
  private String iur;

  private String debtPositionTypeCode;

  private String label;

  private LocalDateIntervalFilter lastClassificationDate;
  private LocalDateIntervalFilter regulationDate;
  private LocalDateIntervalFilter billDate;
  private LocalDateIntervalFilter regionValueDate;

  private String debtorUniqueIdentifierCode;
  private String payerUniqueIdentifierCode;
  private String pspLastName;
  private String accountRegistryCode;
  private String billAmountCents;
}
