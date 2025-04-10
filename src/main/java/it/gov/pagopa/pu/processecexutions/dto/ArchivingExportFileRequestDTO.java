package it.gov.pagopa.pu.processecexutions.dto;

import it.gov.pagopa.pu.processecexutions.model.exportfile.ArchivingExportFileFilter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ArchivingExportFileRequestDTO extends ExportFileRequestDTO<ArchivingExportFileFilter>{
}
