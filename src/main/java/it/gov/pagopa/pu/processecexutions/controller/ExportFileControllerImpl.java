package it.gov.pagopa.pu.processecexutions.controller;

import it.gov.pagopa.pu.processecexutions.controller.generated.ExportFileControllerApi;
import it.gov.pagopa.pu.processecexutions.dto.exportFile.*;
import it.gov.pagopa.pu.processecexutions.enums.ExportFileTypeEnum;
import it.gov.pagopa.pu.processecexutions.model.exportfile.ExportFileFilter;
import it.gov.pagopa.pu.processecexutions.service.ExportFileSaveService;
import it.gov.pagopa.pu.processecexutions.util.ExportConstants;
import it.gov.pagopa.pu.processecexutions.util.SecurityUtils;
import java.net.URI;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ExportFileControllerImpl implements ExportFileControllerApi {

  private final ExportFileSaveService service;

  public ExportFileControllerImpl(ExportFileSaveService service) {
    this.service = service;
  }

  @Override
  public ResponseEntity<Void> createPaidExportFile(
    PaidExportFileRequestDTO paidExportFileRequestDTO) {
    return createExportFile(paidExportFileRequestDTO);
  }

  @Override
  public ResponseEntity<Void> createClassificationsExportFile(
    ClassificationsExportFileRequestDTO classificationExportFileRequestDTO) {
    return createExportFile(classificationExportFileRequestDTO);
  }

  @Override
  public ResponseEntity<Void> createPaymentsReportingExportFile(
    PaymentsReportingExportFileRequestDTO paymentsReportingExportFileRequestDTO) {
    return createExportFile(paymentsReportingExportFileRequestDTO);
  }

  @Override
  public ResponseEntity<Void> createReceiptsArchivingExportFile(ReceiptsArchivingExportFileRequestDTO receiptsArchivingExportFileRequestDTO) {
    return createExportFile(receiptsArchivingExportFileRequestDTO);
  }

  private <R extends ExportFileFilter> ResponseEntity<Void> createExportFile(
    ExportFileRequestDTO<R> exportFileRequestDTO) {
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
  public ResponseEntity<List<String>> getExportFileTypeVersions(ExportFileTypeEnum exportFileType) {
    return ResponseEntity.ok(ExportConstants.getAvailableVersions(exportFileType));
  }
}
