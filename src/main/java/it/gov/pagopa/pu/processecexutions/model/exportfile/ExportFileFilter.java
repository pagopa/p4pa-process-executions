package it.gov.pagopa.pu.processecexutions.model.exportfile;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

@Schema(oneOf = {
  ClassificationsExportFileFilter.class,
  PaidExportFileFilter.class,
  PaymentsReportingExportFileFilter.class
})
public interface ExportFileFilter extends Serializable {
}
