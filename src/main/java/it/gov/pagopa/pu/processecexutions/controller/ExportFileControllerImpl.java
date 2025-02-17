package it.gov.pagopa.pu.processecexutions.controller;

import it.gov.pagopa.pu.processecexutions.controller.generated.ExportFileControllerApi;
import it.gov.pagopa.pu.processecexutions.dto.ExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.service.ExportFileService;
import it.gov.pagopa.pu.processecexutions.util.SecurityUtils;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExportFileControllerImpl implements ExportFileControllerApi {

  private final ExportFileService service;

  public ExportFileControllerImpl(ExportFileService service) {
    this.service = service;
  }

  @Override
  public ResponseEntity<Void> createExportFile(
    ExportFileRequestDTO exportFileRequestDTO) {
    return ResponseEntity
      .created(URI.create(String.valueOf(
        service.handleUploaded(exportFileRequestDTO,
          SecurityUtils.getCurrentUserExternalId(),
          SecurityUtils.getAccessToken()).getExportFileId())))
      .build();
  }

}
