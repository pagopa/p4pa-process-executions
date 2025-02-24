package it.gov.pagopa.pu.processecexutions.controller;

import it.gov.pagopa.pu.processecexutions.controller.generated.ExportFileControllerApi;
import it.gov.pagopa.pu.processecexutions.dto.ExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.service.ExportFileService;
import it.gov.pagopa.pu.processecexutions.util.SecurityUtils;
import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ExportFileControllerImpl implements ExportFileControllerApi {

  private final ExportFileService service;

  public ExportFileControllerImpl(ExportFileService service) {
    this.service = service;
  }

  @Override
  @SuppressWarnings("rawtypes")
  public ResponseEntity<Void> createExportFile(
    ExportFileRequestDTO exportFileRequestDTO) {
    log.info(
      "The user has requested export file with organizationId {} and flowFileType {}",
      exportFileRequestDTO.getOrganizationId(),
      exportFileRequestDTO.getFlowFileType());
    return ResponseEntity
      .created(URI.create(String.valueOf(
        service.save(exportFileRequestDTO,
          SecurityUtils.getCurrentUserExternalId(),
          SecurityUtils.getAccessToken()).getExportFileId())))
      .build();
  }

}
