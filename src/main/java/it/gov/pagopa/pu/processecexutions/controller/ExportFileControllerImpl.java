package it.gov.pagopa.pu.processecexutions.controller;

import it.gov.pagopa.pu.processecexutions.controller.generated.ExportFileControllerApi;
import it.gov.pagopa.pu.processecexutions.dto.ExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.enums.ExportFileType;
import it.gov.pagopa.pu.processecexutions.service.ExportFileDownloadService;
import it.gov.pagopa.pu.processecexutions.util.ExportConstants;
import it.gov.pagopa.pu.processecexutions.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class ExportFileControllerImpl implements ExportFileControllerApi {

  private final ExportFileDownloadService service;

  public ExportFileControllerImpl(ExportFileDownloadService service) {
    this.service = service;
  }

  @Override
  @SuppressWarnings("rawtypes")
  public ResponseEntity<Void> createExportFile(
    ExportFileRequestDTO exportFileRequestDTO) {
    log.info(
      "The user has requested export file with organizationId {} and exportFileType {}",
      exportFileRequestDTO.getOrganizationId(),
      exportFileRequestDTO.getExportFileType());
    return ResponseEntity
      .created(URI.create(String.valueOf(
        service.save(exportFileRequestDTO,
          SecurityUtils.getCurrentUserExternalId(),
          SecurityUtils.getAccessToken()).getExportFileId())))
      .build();
  }

  @Override
  public ResponseEntity<List<String>> getExportFileTypeVersions(ExportFileType exportFileType) {
    return ResponseEntity.ok(ExportConstants.getAvailableVersions(exportFileType));
  }
}
