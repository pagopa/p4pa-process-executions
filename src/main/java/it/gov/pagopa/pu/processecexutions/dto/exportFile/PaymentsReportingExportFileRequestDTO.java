package it.gov.pagopa.pu.processecexutions.dto.exportFile;

import it.gov.pagopa.pu.processecexutions.model.exportfile.PaymentsReportingExportFileFilter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class PaymentsReportingExportFileRequestDTO extends ExportFileRequestDTO<PaymentsReportingExportFileFilter> {
}
