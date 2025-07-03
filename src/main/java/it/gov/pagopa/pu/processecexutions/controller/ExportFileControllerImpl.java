package it.gov.pagopa.pu.processecexutions.controller;

import it.gov.pagopa.pu.processecexutions.controller.generated.ExportFileControllerApi;
import it.gov.pagopa.pu.processecexutions.dto.LocalDateIntervalFilter;
import it.gov.pagopa.pu.processecexutions.dto.OffsetDateTimeIntervalFilter;
import it.gov.pagopa.pu.processecexutions.dto.exportFile.*;
import it.gov.pagopa.pu.processecexutions.enums.ExportFileTypeEnum;
import it.gov.pagopa.pu.processecexutions.exception.InvalidParamException;
import it.gov.pagopa.pu.processecexutions.exception.InvalidTimeRangeException;
import it.gov.pagopa.pu.processecexutions.model.exportfile.*;
import it.gov.pagopa.pu.processecexutions.service.ExportFileSaveService;
import it.gov.pagopa.pu.processecexutions.util.ExportConstants;
import it.gov.pagopa.pu.processecexutions.util.SecurityUtils;
import it.gov.pagopa.pu.processecexutions.util.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@Slf4j
public class ExportFileControllerImpl implements ExportFileControllerApi {

  private final ExportFileSaveService service;
  private final Integer exportPaidMaxMonthsInterval;
  private final Integer exportArchivingMaxMonthsInterval;
  private final Integer classificationMaxMonthsInterval;
  private static final String INVALID_DATE_TIME_RANGE_EXCEPTION_MESSAGE = "The date interval between %s and %s cannot exceed %d months";

  public ExportFileControllerImpl(ExportFileSaveService service,
                                  @Value("${data-export.installment-paid-view.max-months-interval}") Integer exportPaidMaxMonthsInterval,
                                  @Value("${data-export.receipt-archiving-view.max-months-interval}") Integer exportArchivingMaxMonthsInterval,
                                  @Value("${data-export.classification-view.max-months-interval}") Integer classificationMaxMonthsInterval) {
    this.service = service;
    this.exportPaidMaxMonthsInterval = exportPaidMaxMonthsInterval;
    this.exportArchivingMaxMonthsInterval = exportArchivingMaxMonthsInterval;
    this.classificationMaxMonthsInterval = classificationMaxMonthsInterval;
  }

  @Override
  public ResponseEntity<Void> createPaidExportFile(PaidExportFileRequestDTO paidExportFileRequestDTO) {
    PaidExportFileFilter filterFields = paidExportFileRequestDTO.getFilterFields();

    if (filterFields != null){
      validatePaidExportFilterFieldsDate(filterFields);
    }
    return createExportFile(paidExportFileRequestDTO);
  }

  private void validatePaidExportFilterFieldsDate(PaidExportFileFilter filterFields) {
    OffsetDateTimeIntervalFilter paymentDateTime = filterFields.getPaymentDateTime();
    OffsetDateTimeIntervalFilter installmentUpdateDateTime = filterFields.getInstallmentUpdateDateTime();
    boolean hasPaymentDates = paymentDateTime != null && paymentDateTime .getFrom() != null && paymentDateTime.getTo() != null;
    boolean hasInstallmentDates = installmentUpdateDateTime != null && installmentUpdateDateTime.getFrom() != null && installmentUpdateDateTime.getTo() != null;

    if (hasPaymentDates == hasInstallmentDates) {
      throw new InvalidParamException(
        "You must provide only one of the following date ranges: either the payment date range (paymentDateTimeFrom and paymentDateTimeTo) or the installment update date range (installmentUpdateDateTimeFrom and installmentUpdateDateTimeTo). Providing both or neither is not allowed"
      );
    }

    if (hasPaymentDates) {
      validateOffsetDateTimeRange(paymentDateTime, exportPaidMaxMonthsInterval);
    }

    if (hasInstallmentDates) {
      validateOffsetDateTimeRange(installmentUpdateDateTime, exportPaidMaxMonthsInterval);
    }
  }

  @Override
  public ResponseEntity<Void> createClassificationsExportFile(ClassificationsExportFileRequestDTO request) {
    ClassificationsExportFileFilter filter = request.getFilterFields();
    if (filter != null) {
      validateLocalDateRange(filter.getLastClassificationDate(), classificationMaxMonthsInterval);
      validateLocalDateRange(filter.getPayDate(), classificationMaxMonthsInterval);
      validateLocalDateRange(filter.getPaymentDate(), classificationMaxMonthsInterval);
      validateLocalDateRange(filter.getRegulationDate(), classificationMaxMonthsInterval);
      validateLocalDateRange(filter.getBillDate(), classificationMaxMonthsInterval);
      validateLocalDateRange(filter.getRegionValueDate(), classificationMaxMonthsInterval);
    }
    return createExportFile(request);
  }

  @Override
  public ResponseEntity<Void> createPaymentsReportingExportFile(
    PaymentsReportingExportFileRequestDTO paymentsReportingExportFileRequestDTO) {
    return createExportFile(paymentsReportingExportFileRequestDTO);
  }

  @Override
  public ResponseEntity<Void> createReceiptsArchivingExportFile(ReceiptsArchivingExportFileRequestDTO receiptsArchivingExportFileRequestDTO) {
    ReceiptsArchivingExportFileFilter filterFields = receiptsArchivingExportFileRequestDTO.getFilterFields();
    if (filterFields != null){
      validateOffsetDateTimeRange(filterFields.getPaymentDateTime(), exportArchivingMaxMonthsInterval);
    }

    return createExportFile(receiptsArchivingExportFileRequestDTO);
  }

  private void validateOffsetDateTimeRange(OffsetDateTimeIntervalFilter range, int maxMonths) {
    if (!Utilities.isValidIntervalBetweenOffsetDateTime(range.getFrom(), range.getTo(), ChronoUnit.MONTHS, maxMonths)) {
      throw new InvalidTimeRangeException(INVALID_DATE_TIME_RANGE_EXCEPTION_MESSAGE.formatted(range.getFrom(), range.getTo(), maxMonths));
    }
  }

  private void validateLocalDateRange(LocalDateIntervalFilter range, int maxMonths) {
    if (!Utilities.isValidIntervalBetweenLocalDate(range.getFrom(), range.getTo(), ChronoUnit.MONTHS, maxMonths)) {
      throw new InvalidTimeRangeException(INVALID_DATE_TIME_RANGE_EXCEPTION_MESSAGE.formatted(range.getFrom(), range.getTo(), maxMonths));
    }
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
  public ResponseEntity<List<ExportFileTypeVersions>> getExportFileTypeVersions(ExportFileTypeEnum exportFileType) {
    return ResponseEntity.ok(ExportConstants.getAvailableVersions(exportFileType));
  }
}
