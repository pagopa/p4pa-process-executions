package it.gov.pagopa.pu.processecexutions.model.exportfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaidExportFileFilter implements ExportFileFilter {
  private String iuv;
}
