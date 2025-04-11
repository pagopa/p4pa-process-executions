package it.gov.pagopa.pu.processecexutions.dto.exportFile;

import it.gov.pagopa.pu.processecexutions.model.exportfile.PaidExportFileFilter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class PaidExportFileRequestDTO extends ExportFileRequestDTO<PaidExportFileFilter> {
}
