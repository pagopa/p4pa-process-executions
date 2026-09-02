package it.gov.pagopa.pu.processexecutions.dto.exportFile;

import it.gov.pagopa.pu.processexecutions.model.exportfile.PaymentsReportingExportFileFilter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class PaymentsReportingExportFileRequestDTO extends ExportFileRequestDTO<PaymentsReportingExportFileFilter> {
}
