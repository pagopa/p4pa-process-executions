package it.gov.pagopa.pu.processecexutions.service;

import it.gov.pagopa.pu.processecexutions.dto.exportFile.ExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.model.ExportFile;
import it.gov.pagopa.pu.processecexutions.model.exportfile.ExportFileFilter;

public interface ExportFileSaveService {
  <R extends ExportFileFilter> ExportFile<R> save(ExportFileRequestDTO<R> requestDTO, String operatorExternalId,
    String accessToken);
}
