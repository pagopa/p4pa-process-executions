package it.gov.pagopa.pu.processecexutions.controller;

import it.gov.pagopa.pu.processecexutions.controller.generated.IngestionFlowFileControllerApi;
import it.gov.pagopa.pu.processecexutions.dto.generated.IngestionFlowFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.service.IngestionFlowFileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class IngestionFlowFileControllerImpl implements IngestionFlowFileControllerApi {

  private final IngestionFlowFileService service;

  public IngestionFlowFileControllerImpl(IngestionFlowFileService service) {
    this.service = service;
  }

  @Override
  public ResponseEntity<Void> createIngestionFlowFile(IngestionFlowFileRequestDTO ingestionFlowFileRequestDTO) {
    return ResponseEntity
      .created(URI.create(String.valueOf(service.handleUploaded(ingestionFlowFileRequestDTO).getIngestionFlowFileId())))
      .build();
  }

}
