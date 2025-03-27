package it.gov.pagopa.pu.processecexutions.dto;

import it.gov.pagopa.pu.processecexutions.enums.ExportFileType;
import it.gov.pagopa.pu.processecexutions.model.exportfile.ExportFileFilter;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExportFileRequestDTO <T extends ExportFileFilter> {
  @NotNull
  private Long organizationId;
  @NotNull
  private ExportFileType flowFileType;
  @NotNull
  private String flowFileVersion;
  @NotNull
  private T filterFields;
}
