package it.gov.pagopa.pu.processecexutions.service;

import it.gov.pagopa.pu.processecexutions.dto.generated.ExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.model.ExportFile;

public interface ExportFileService {
  ExportFile<?> handleUploaded(ExportFileRequestDTO requestDTO, String operatorExternalId,
    String accessToken);
}
