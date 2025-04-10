package it.gov.pagopa.pu.processecexutions.model.exportfile;

import it.gov.pagopa.pu.processecexutions.dto.OffsetDateTimeIntervalFilter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArchivingExportFileFilter implements ExportFileFilter {

  private OffsetDateTimeIntervalFilter paymentDate;
}
