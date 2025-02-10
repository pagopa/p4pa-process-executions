package it.gov.pagopa.pu.processecexutions.controller;

import it.gov.pagopa.pu.processecexutions.controller.generated.IngestionFlowFileEntityExtendedControllerApi;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileStatus;
import it.gov.pagopa.pu.processecexutions.repository.IngestionFlowFileRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to host spring-data-rest directly not supported methods
 */
@RestController
public class IngestionFlowFileEntityExtendedController implements IngestionFlowFileEntityExtendedControllerApi {

  private final IngestionFlowFileRepository repository;

  public IngestionFlowFileEntityExtendedController(IngestionFlowFileRepository repository) {
    this.repository = repository;
  }

  @Override
  public ResponseEntity<Integer> updateStatus(Long ingestionFlowFileId, IngestionFlowFileStatus status,
                                              String errorDescription,String discardFile) {
    return ResponseEntity.ok(repository.updateStatus(ingestionFlowFileId, status, errorDescription, discardFile));
  }
}
