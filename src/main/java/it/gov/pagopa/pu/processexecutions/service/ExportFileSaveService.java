package it.gov.pagopa.pu.processexecutions.service;

import it.gov.pagopa.pu.processexecutions.dto.exportFile.ExportFileRequestDTO;
import it.gov.pagopa.pu.processexecutions.model.ExportFile;
import it.gov.pagopa.pu.processexecutions.model.exportfile.ExportFileFilter;

public interface ExportFileSaveService {
  <R extends ExportFileFilter> ExportFile<R> save(ExportFileRequestDTO<R> requestDTO, String operatorExternalId,
    String accessToken);
}
