package it.gov.pagopa.pu.processecexutions.controller;

import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileStatus;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileType;
import it.gov.pagopa.pu.processecexutions.model.IngestionFlowFile;
import it.gov.pagopa.pu.processecexutions.repository.IngestionFlowFileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/** Controller to host spring-data-rest directly not supported methods */
@RestController
@RequestMapping("/crud-ext/ingestion-flow-files")
public class IngestionFlowFileEntityExtendedController {

  private final IngestionFlowFileRepository repository;

  public IngestionFlowFileEntityExtendedController(IngestionFlowFileRepository repository) {
    this.repository = repository;
  }

  @PatchMapping("{ingestionFlowFileId}/set-status")
  public int updateStatus(
          @PathVariable Long ingestionFlowFileId,
          @RequestParam IngestionFlowFileStatus status,
          @RequestParam(required = false) String codError,
          @RequestParam(required = false) String discardFile){
    return repository.updateStatus(ingestionFlowFileId, status, codError, discardFile);
  }
}
