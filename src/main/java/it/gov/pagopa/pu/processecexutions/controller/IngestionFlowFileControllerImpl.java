package it.gov.pagopa.pu.processecexutions.controller;

import it.gov.pagopa.pu.processecexutions.controller.generated.IngestionFlowFileControllerApi;
import it.gov.pagopa.pu.processecexutions.dto.generated.IngestionFlowFileDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IngestionFlowFileControllerImpl implements IngestionFlowFileControllerApi {

  @Override
  public ResponseEntity<IngestionFlowFileDTO> createIngestionFlowFile(IngestionFlowFileDTO ingestionFlowFileDTO) {
    return ResponseEntity.ok(null);
  }
}
