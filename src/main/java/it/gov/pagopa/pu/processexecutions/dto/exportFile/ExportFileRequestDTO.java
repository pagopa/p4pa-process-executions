package it.gov.pagopa.pu.processexecutions.dto.exportFile;

import it.gov.pagopa.pu.processexecutions.enums.ExportFileTypeEnum;
import it.gov.pagopa.pu.processexecutions.model.exportfile.ExportFileFilter;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Data
public abstract class ExportFileRequestDTO <T extends ExportFileFilter> {
  @NotNull
  private Long organizationId;
  @NotNull
  private ExportFileTypeEnum exportFileType;
  @NotNull
  private String fileVersion;
  @NotNull
  private T filterFields;
}
