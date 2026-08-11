package it.gov.pagopa.pu.processexecutions.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum IngestionFlowFileStatus {
  UPLOADED,
  PROCESSING,
  COMPLETED,
  ERROR,
  WAITING_FILE,
  WARNING
}
