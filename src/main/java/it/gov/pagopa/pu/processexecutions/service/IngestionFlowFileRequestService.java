package it.gov.pagopa.pu.processexecutions.service;

import it.gov.pagopa.pu.processexecutions.dto.generated.IngestionFlowFileRequestDTO;
import it.gov.pagopa.pu.processexecutions.model.IngestionFlowFile;

public interface IngestionFlowFileRequestService {
  IngestionFlowFile handleUploaded(IngestionFlowFileRequestDTO requestDTO, String operatorExternalId,
    String accessToken);
  IngestionFlowFile handleReservation(IngestionFlowFileRequestDTO requestDTO, String operatorExternalId);
}
