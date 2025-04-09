package it.gov.pagopa.pu.processecexutions.dto;

import it.gov.pagopa.pu.processecexutions.model.exportfile.ClassificationsExportFileFilter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ClassificationsExportFileRequestDTO extends ExportFileRequestDTO<ClassificationsExportFileFilter> {
}
