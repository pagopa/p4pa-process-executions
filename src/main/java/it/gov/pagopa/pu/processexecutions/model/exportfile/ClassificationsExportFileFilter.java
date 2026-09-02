package it.gov.pagopa.pu.processexecutions.model.exportfile;

import it.gov.pagopa.pu.classification.dto.generated.ClassificationsEnum;
import it.gov.pagopa.pu.processexecutions.dto.LocalDateIntervalFilter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassificationsExportFileFilter implements ExportFileFilter {

  private List<String> iufs;
  private String iud;
  private List<String> iuvs;
  private List<String> iurs;

  private Set<ClassificationsEnum> label;

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
  private Set<String> debtPositionTypeOrgCodes;

}
