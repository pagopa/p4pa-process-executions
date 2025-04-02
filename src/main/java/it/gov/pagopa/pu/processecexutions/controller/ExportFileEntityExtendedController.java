package it.gov.pagopa.pu.processecexutions.controller;

import it.gov.pagopa.pu.processecexutions.controller.generated.ExportFileEntityExtendedControllerApi;
import it.gov.pagopa.pu.processecexutions.enums.ExportFileStatus;
import it.gov.pagopa.pu.processecexutions.repository.exportfile.ExportFileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to host spring-data-rest directly not supported methods
 */
@RestController
public class ExportFileEntityExtendedController implements
  ExportFileEntityExtendedControllerApi {

  private final ExportFileRepository repository;

  public ExportFileEntityExtendedController(ExportFileRepository repository) {
    this.repository = repository;
  }

  @Override
  public ResponseEntity<Integer> updateExportFileStatus(Long ingestionFlowFileId, ExportFileStatus oldStatus, ExportFileStatus newStatus,
                        String filePath, String fileName, Long numTotalRows, Long fileSize, String errorDescription) {
    int result = repository.updateStatus(ingestionFlowFileId, oldStatus, newStatus, filePath, fileName, numTotalRows, fileSize, errorDescription);
    if(result>0){
      return ResponseEntity.ok(result);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }
}
