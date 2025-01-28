package it.gov.pagopa.pu.processecexutions.model.exportfile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentsReportingExportFileFilter implements ExportFileFilter {
  private String iuf;
}
