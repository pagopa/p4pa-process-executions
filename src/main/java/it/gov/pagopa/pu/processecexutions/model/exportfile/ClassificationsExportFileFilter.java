package it.gov.pagopa.pu.processecexutions.model.exportfile;

import it.gov.pagopa.pu.classification.dto.generated.ClassificationsEnum;
import it.gov.pagopa.pu.processecexutions.dto.OffsetDateTimeIntervalFilter;
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

  private OffsetDateTimeIntervalFilter lastClassificationDate;
  private OffsetDateTimeIntervalFilter payDate;
  private OffsetDateTimeIntervalFilter paymentDate;
  private OffsetDateTimeIntervalFilter regulationDate;
  private OffsetDateTimeIntervalFilter billDate;
  private OffsetDateTimeIntervalFilter regionValueDate;

  private String regulationUniqueIdentifier;
  private String accountRegistryCode;
  private Long billAmountCents;
  private String remittanceInformation;
  private String pspCompanyName;
  private String pspLastName;

}
