package it.gov.pagopa.pu.processecexutions.dto;

import it.gov.pagopa.pu.processecexutions.enums.ExportFileType;
import it.gov.pagopa.pu.processecexutions.model.exportfile.ExportFileFilter;
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
  private ExportFileType exportFileType;
  @NotNull
  private String fileVersion;
  @NotNull
  private T filterFields;
}
