package it.gov.pagopa.pu.processecexutions.dto;

import it.gov.pagopa.pu.processecexutions.enums.ExportFlowFileType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExportFileRequestDTO {
  @NotNull
  private Long organizationId;
  @NotNull
  private ExportFlowFileType flowFileType;
}
