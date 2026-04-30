package it.gov.pagopa.pu.processecexutions.model.exportfile;

import it.gov.pagopa.pu.processecexutions.dto.OffsetDateTimeIntervalFilter;
import it.gov.pagopa.pu.workflowhub.dto.generated.DebtPositionOrigin;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaidExportFileFilter implements ExportFileFilter {

  private OffsetDateTimeIntervalFilter paymentDateTime;
  private OffsetDateTimeIntervalFilter installmentUpdateDateTime;
  private Long debtPositionTypeOrgId;
  private List<DebtPositionOrigin> debtPositionOrigins;
}
