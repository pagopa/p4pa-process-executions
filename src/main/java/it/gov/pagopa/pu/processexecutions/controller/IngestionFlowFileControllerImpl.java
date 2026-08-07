package it.gov.pagopa.pu.processexecutions.controller;

import it.gov.pagopa.pu.processexecutions.controller.generated.IngestionFlowFileControllerApi;
import it.gov.pagopa.pu.processexecutions.dto.generated.IngestionFlowFileRequestDTO;
import it.gov.pagopa.pu.processexecutions.enums.IngestionFlowFileTypeEnum;
import it.gov.pagopa.pu.processexecutions.service.IngestionFlowFileRequestService;
import it.gov.pagopa.pu.processexecutions.util.IngestionFlowFileConstants;
import it.gov.pagopa.pu.processexecutions.util.SecurityUtils;
import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IngestionFlowFileControllerImpl implements IngestionFlowFileControllerApi {

  private final IngestionFlowFileRequestService service;

  public IngestionFlowFileControllerImpl(IngestionFlowFileRequestService service) {
    this.service = service;
  }

  @Override
  public ResponseEntity<Void> createIngestionFlowFile(IngestionFlowFileRequestDTO ingestionFlowFileRequestDTO) {
    return ResponseEntity
      .created(URI.create(String.valueOf(service.handleUploaded(ingestionFlowFileRequestDTO, SecurityUtils.getCurrentUserExternalId(), SecurityUtils.getAccessToken()).getIngestionFlowFileId())))
      .build();
  }

  @Override
  public ResponseEntity<Long> createIngestionFlowFileReservation(IngestionFlowFileRequestDTO ingestionFlowFileRequestDTO) {
    return ResponseEntity.ok(service.handleReservation(ingestionFlowFileRequestDTO, SecurityUtils.getCurrentUserExternalId()).getIngestionFlowFileId());
  }

  @Override
  public ResponseEntity<List<String>> getIngestionFlowFileVersion(IngestionFlowFileTypeEnum ingestionFlowFileType) {
    return ResponseEntity.ok(IngestionFlowFileConstants.getAvailableVersions(ingestionFlowFileType));
  }
}
