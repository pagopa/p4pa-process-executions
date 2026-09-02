package it.gov.pagopa.pu.processexecutions.controller;

import it.gov.pagopa.pu.processexecutions.controller.generated.ExportFileEntityExtendedControllerApi;
import it.gov.pagopa.pu.processexecutions.enums.ExportFileStatus;
import it.gov.pagopa.pu.processexecutions.repository.exportfile.ExportFileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

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
  public ResponseEntity<Integer> updateExportFileStatus(Long exportFileId,
                                                        ExportFileStatus oldStatus,
                                                        ExportFileStatus newStatus,
                                                        String filePathName,
                                                        String fileName,
                                                        Long fileSize,
                                                        Long numTotalRows,
                                                        String errorDescription,
                                                        OffsetDateTime expirationDate) {
    int result = repository.updateStatus(exportFileId, oldStatus, newStatus, filePathName, fileName, fileSize, numTotalRows, errorDescription, expirationDate);
    if(result>0){
      return ResponseEntity.ok(result);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }
}
