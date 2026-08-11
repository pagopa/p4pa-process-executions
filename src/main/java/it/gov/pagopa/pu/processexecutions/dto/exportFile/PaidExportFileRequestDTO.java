package it.gov.pagopa.pu.processexecutions.dto.exportFile;

import it.gov.pagopa.pu.processexecutions.model.exportfile.PaidExportFileFilter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class PaidExportFileRequestDTO extends ExportFileRequestDTO<PaidExportFileFilter> {
}
