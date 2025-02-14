package it.gov.pagopa.pu.processecexutions.service;

import it.gov.pagopa.pu.processecexutions.dto.generated.ExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.dto.generated.IngestionFlowFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.model.ExportFile;
import it.gov.pagopa.pu.processecexutions.model.IngestionFlowFile;

public interface ExportFileService<T extends ExportFile<?>> {
  T handleUploaded(ExportFileRequestDTO requestDTO, String operatorExternalId,
    String accessToken);
}
