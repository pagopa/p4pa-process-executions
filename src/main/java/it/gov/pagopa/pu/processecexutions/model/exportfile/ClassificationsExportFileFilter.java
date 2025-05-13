package it.gov.pagopa.pu.processecexutions.model.exportfile;

import it.gov.pagopa.pu.classification.dto.generated.ClassificationsEnum;
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
  private String iud;
  private String iuv;
  private String iur;

  private ClassificationsEnum label;

  private LocalDateIntervalFilter lastClassificationDate;
  private LocalDateIntervalFilter payDate;
  private LocalDateIntervalFilter paymentDate;
  private LocalDateIntervalFilter regulationDate;
  private LocalDateIntervalFilter billDate;
  private LocalDateIntervalFilter regionValueDate;

  private String regulationUniqueIdentifier;
  private String accountRegistryCode;
  private Long billAmountCents;
  private String remittanceInformation;
  private String pspCompanyName;
  private String pspLastName;

}
