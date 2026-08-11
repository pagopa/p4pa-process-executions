package it.gov.pagopa.pu.processexecutions.controller;

import it.gov.pagopa.pu.processexecutions.controller.generated.IngestionFlowFileEntityExtendedControllerApi;
import it.gov.pagopa.pu.processexecutions.dto.generated.IngestionFlowFileUpdateStatusRequestDTO;
import it.gov.pagopa.pu.processexecutions.repository.IngestionFlowFileRepository;
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
                                              IngestionFlowFileUpdateStatusRequestDTO requestDTO) {
    int result = repository.updateStatus(ingestionFlowFileId, requestDTO.getFileVersion(), requestDTO.getOldStatus(), requestDTO.getNewStatus(), requestDTO.getProcessedRows(), requestDTO.getTotalRows(), requestDTO.getErrorDescription(), requestDTO.getDiscardFile());
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
