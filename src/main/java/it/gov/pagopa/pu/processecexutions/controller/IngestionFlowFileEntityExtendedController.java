package it.gov.pagopa.pu.processecexutions.controller;

import it.gov.pagopa.pu.processecexutions.controller.generated.IngestionFlowFileEntityExtendedControllerApi;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileStatus;
import it.gov.pagopa.pu.processecexutions.repository.IngestionFlowFileRepository;
import org.springframework.http.HttpStatus;
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
  public ResponseEntity<Integer> updateStatus(Long ingestionFlowFileId,
                                              IngestionFlowFileStatus oldStatus, IngestionFlowFileStatus newStatus,
                                              Long processedRows, Long totalRows,
                                              String fileVersion, String errorDescription, String discardFile) {
    int result = repository.updateStatus(ingestionFlowFileId, fileVersion, oldStatus, newStatus, processedRows, totalRows, errorDescription, discardFile);
    if (result > 0) {
      return ResponseEntity.ok(result);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @Override
  public ResponseEntity<Integer> updateFileNames(Long ingestionFlowFileId, String fileName, String discardFileName) {
    int result = repository.updateFileNames(ingestionFlowFileId, fileName, discardFileName);
    if (result > 0) {
      return ResponseEntity.ok(result);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @Override
  public ResponseEntity<Integer> updatePdfGenerated(Long ingestionFlowFileId, Long pdfGenerated, String folderId) {
    int result = repository.updatePdfGeneratedAndPdfGeneratedId(ingestionFlowFileId, pdfGenerated, folderId);
    if (result > 0) {
      return ResponseEntity.ok(result);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }
}
