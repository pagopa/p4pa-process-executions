package it.gov.pagopa.pu.processexecutions.controller;

import it.gov.pagopa.pu.processexecutions.controller.generated.ExportFileControllerApi;
import it.gov.pagopa.pu.processexecutions.dto.LocalDateIntervalFilter;
import it.gov.pagopa.pu.processexecutions.dto.OffsetDateTimeIntervalFilter;
import it.gov.pagopa.pu.processexecutions.dto.exportFile.*;
import it.gov.pagopa.pu.processexecutions.enums.ExportFileTypeEnum;
import it.gov.pagopa.pu.processexecutions.exception.InvalidParamException;
import it.gov.pagopa.pu.processexecutions.exception.InvalidTimeRangeException;
import it.gov.pagopa.pu.processexecutions.model.exportfile.*;
import it.gov.pagopa.pu.processexecutions.service.ExportFileSaveService;
import it.gov.pagopa.pu.processexecutions.util.ErrorCodeConstants;
import it.gov.pagopa.pu.processexecutions.util.ExportConstants;
import it.gov.pagopa.pu.processexecutions.util.SecurityUtils;
import it.gov.pagopa.pu.processexecutions.util.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static it.gov.pagopa.pu.processexecutions.util.Utilities.isDateFilterConfigured;

@RestController
@Slf4j
public class ExportFileControllerImpl implements ExportFileControllerApi {

  private final ExportFileSaveService service;
  private final Integer exportPaidMaxMonthsInterval;
  private final Integer exportArchivingMaxMonthsInterval;
  private final Integer classificationMaxMonthsInterval;
  private static final String INVALID_DATE_TIME_RANGE_EXCEPTION_MESSAGE = "The date interval between %s and %s cannot exceed %d months";
  private static final String PAYMENT_DATE_TIME_FILTER_NAME = "paymentDateTime";

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

    boolean hasPaymentDates = isDateFilterConfigured(paymentDateTime, PAYMENT_DATE_TIME_FILTER_NAME);
    boolean hasInstallmentDates = isDateFilterConfigured(installmentUpdateDateTime, "installmentUpdateDateTime");

    if (hasPaymentDates == hasInstallmentDates) {
      throw new InvalidParamException(ErrorCodeConstants.ERROR_CODE_INVALID_DATE_FILTER_COMBINATION,
        "You must provide only one of the following date ranges: either the payment date range (paymentDateTimeFrom and paymentDateTimeTo) or the installment update date range (installmentUpdateDateTimeFrom and installmentUpdateDateTimeTo). Providing both or neither is not allowed"
      );
    }

    if (hasPaymentDates) {
      validateOffsetDateTimeRange(paymentDateTime, PAYMENT_DATE_TIME_FILTER_NAME, exportPaidMaxMonthsInterval);
    }

    if (hasInstallmentDates) {
      validateOffsetDateTimeRange(installmentUpdateDateTime, "installmentUpdateDateTime", exportPaidMaxMonthsInterval);
    }
  }

  @Override
  public ResponseEntity<Void> createClassificationsExportFile(ClassificationsExportFileRequestDTO request) {
    ClassificationsExportFileFilter filter = request.getFilterFields();
    if (filter != null) {
      validateLocalDateRange(filter.getLastClassificationDate(),"lastClassificationDate", classificationMaxMonthsInterval);
      validateLocalDateRange(filter.getPayDate(),"payDate", classificationMaxMonthsInterval);
      validateLocalDateRange(filter.getPaymentDate(), "paymentDate", classificationMaxMonthsInterval);
      validateLocalDateRange(filter.getRegulationDate(), "regulationDate", classificationMaxMonthsInterval);
      validateLocalDateRange(filter.getBillDate(), "billDate", classificationMaxMonthsInterval);
      validateLocalDateRange(filter.getRegionValueDate(), "regionValueDate", classificationMaxMonthsInterval);
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
      validateOffsetDateTimeRange(filterFields.getPaymentDateTime(), PAYMENT_DATE_TIME_FILTER_NAME,  exportArchivingMaxMonthsInterval);
    }

    return createExportFile(receiptsArchivingExportFileRequestDTO);
  }

  private void validateOffsetDateTimeRange(OffsetDateTimeIntervalFilter range, String filterName, int maxMonths) {
    if (range != null && !Utilities.isValidIntervalBetweenOffsetDateTime(range.getFrom(), range.getTo(), filterName, ChronoUnit.MONTHS, maxMonths)) {
        throw new InvalidTimeRangeException(INVALID_DATE_TIME_RANGE_EXCEPTION_MESSAGE.formatted(range.getFrom(), range.getTo(), maxMonths));
    }
  }

  private void validateLocalDateRange(LocalDateIntervalFilter range, String filterName, int maxMonths) {
    if (range != null && !Utilities.isValidIntervalBetweenLocalDate(range.getFrom(), range.getTo(), filterName,ChronoUnit.MONTHS, maxMonths)) {
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
