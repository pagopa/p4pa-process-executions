package it.gov.pagopa.pu.processecexutions.service;

import it.gov.pagopa.pu.processecexutions.dto.generated.IngestionFlowFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.model.IngestionFlowFile;

public interface IngestionFlowFileRequestService {
  IngestionFlowFile handleUploaded(IngestionFlowFileRequestDTO requestDTO, String operatorExternalId,
    String accessToken);
  IngestionFlowFile handleReservation(IngestionFlowFileRequestDTO requestDTO, String operatorExternalId);
}
