package it.gov.pagopa.pu.processecexutions.service;

import it.gov.pagopa.pu.processecexutions.dto.generated.IngestionFlowFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.model.IngestionFlowFile;

public interface IngestionFlowFileService {
  IngestionFlowFile handleUploaded(IngestionFlowFileRequestDTO requestDTO, String operatorExternalId);
}
