package it.gov.pagopa.pu.processecexutions.controller;

import it.gov.pagopa.pu.processecexutions.controller.generated.ExportFileControllerApi;
import it.gov.pagopa.pu.processecexutions.dto.LocalDateIntervalFilter;
import it.gov.pagopa.pu.processecexutions.dto.OffsetDateTimeIntervalFilter;
import it.gov.pagopa.pu.processecexutions.dto.exportFile.ReceiptsArchivingExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.dto.exportFile.ClassificationsExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.dto.exportFile.PaidExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.dto.exportFile.PaymentsReportingExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.enums.ExportFileTypeEnum;
import it.gov.pagopa.pu.processecexutions.exception.InvalidParamException;
import it.gov.pagopa.pu.processecexutions.exception.InvalidTimeRangeException;
import it.gov.pagopa.pu.processecexutions.model.ExportFile;
import it.gov.pagopa.pu.processecexutions.model.exportfile.*;
import it.gov.pagopa.pu.processecexutions.service.ExportFileSaveService;
import it.gov.pagopa.pu.processecexutions.util.ExportConstants;
import it.gov.pagopa.pu.processecexutions.util.SecurityUtilsTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ExportFileControllerApiTest {

  @Mock
  private ExportFileSaveService serviceMock;

  private ExportFileControllerApi controller;
  private static final Integer MAX_MONTHS_RANGE = 6;

  @BeforeEach
  void init(){
    Authentication authentication = new UsernamePasswordAuthenticationToken("fakeUser", "fakeAccessToken");
    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
    securityContext.setAuthentication(authentication);
    SecurityContextHolder.setContext(securityContext);
    controller = new ExportFileControllerImpl(serviceMock, MAX_MONTHS_RANGE, MAX_MONTHS_RANGE, MAX_MONTHS_RANGE);
  }

  @AfterEach
  void verifyNoMoreInteractions(){
    Mockito.verifyNoMoreInteractions(serviceMock);
  }

  @Test
  void whenCreateClassificationsExportFileThenInvokeService() {
    ClassificationsExportFileRequestDTO requestDTO = new ClassificationsExportFileRequestDTO();
    ExportFile<?> t = new ClassificationsExportFile();
    t.setExportFileId(1L);

    String operatorExternalId = "OPERATOREXTERNALID";
    SecurityUtilsTest.configureSecurityContext(operatorExternalId);

    Mockito.doReturn(t).when(serviceMock).save(Mockito.same(requestDTO), Mockito.same(operatorExternalId), Mockito.anyString());

    ResponseEntity<Void> result = controller.createClassificationsExportFile(requestDTO);

    assertEquals(HttpStatus.CREATED, result.getStatusCode());
    assertEquals("1", result.getHeaders().getFirst(HttpHeaders.LOCATION));
  }

  @Test
  void whenCreatePaidExportFileThenInvokeService() {
    PaidExportFileRequestDTO requestDTO = new PaidExportFileRequestDTO();
    ExportFile<?> t = new PaidExportFile();
    t.setExportFileId(1L);

    String operatorExternalId = "OPERATOREXTERNALID";
    SecurityUtilsTest.configureSecurityContext(operatorExternalId);

    Mockito.doReturn(t).when(serviceMock).save(Mockito.same(requestDTO), Mockito.same(operatorExternalId), Mockito.anyString());

    ResponseEntity<Void> result = controller.createPaidExportFile(requestDTO);

    assertEquals(HttpStatus.CREATED, result.getStatusCode());
    assertEquals("1", result.getHeaders().getFirst(HttpHeaders.LOCATION));
  }

  @Test
  void whenBothDateRangesProvided_thenThrowInvalidDatesException() {
    OffsetDateTime now = OffsetDateTime.now();

    OffsetDateTimeIntervalFilter paymentDate = new OffsetDateTimeIntervalFilter();
    paymentDate.setFrom(now.minusDays(5));
    paymentDate.setTo(now);

    OffsetDateTimeIntervalFilter installmentDate = new OffsetDateTimeIntervalFilter();
    installmentDate.setFrom(now.minusDays(10));
    installmentDate.setTo(now.minusDays(1));

    PaidExportFileFilter filter = new PaidExportFileFilter();
    filter.setPaymentDateTime(paymentDate);
    filter.setInstallmentUpdateDateTime(installmentDate);

    PaidExportFileRequestDTO requestDTO = new PaidExportFileRequestDTO();
    requestDTO.setFilterFields(filter);

    assertThrows(InvalidParamException.class, () -> controller.createPaidExportFile(requestDTO));
  }

  @Test
  void whenNoDateRangesProvided_thenThrowInvalidDatesException() {
    PaidExportFileFilter filter = new PaidExportFileFilter();

    PaidExportFileRequestDTO requestDTO = new PaidExportFileRequestDTO();
    requestDTO.setFilterFields(filter);

    assertThrows(InvalidParamException.class, () -> controller.createPaidExportFile(requestDTO));
  }

  @Test
  void whenOnlyPaymentDateProvided_thenInvokeService() {
    OffsetDateTimeIntervalFilter paymentDate = new OffsetDateTimeIntervalFilter();
    paymentDate.setFrom(OffsetDateTime.now().minusDays(5));
    paymentDate.setTo(OffsetDateTime.now());

    PaidExportFileFilter filter = new PaidExportFileFilter();
    filter.setPaymentDateTime(paymentDate);

    PaidExportFileRequestDTO requestDTO = new PaidExportFileRequestDTO();
    requestDTO.setFilterFields(filter);

    ExportFile<?> exportFile = new PaidExportFile();
    exportFile.setExportFileId(1L);

    String operatorExternalId = "OPERATOREXTERNALID";
    SecurityUtilsTest.configureSecurityContext(operatorExternalId);

    Mockito.doReturn(exportFile).when(serviceMock).save(Mockito.same(requestDTO), Mockito.same(operatorExternalId), Mockito.anyString());

    ResponseEntity<Void> result = controller.createPaidExportFile(requestDTO);

    assertEquals(HttpStatus.CREATED, result.getStatusCode());
    assertEquals("1", result.getHeaders().getFirst(HttpHeaders.LOCATION));
  }

  @Test
  void whenOnlyInstallmentUpdateDateProvided_thenInvokeService() {
    OffsetDateTimeIntervalFilter installmentDate = new OffsetDateTimeIntervalFilter();
    installmentDate.setFrom(OffsetDateTime.now().minusDays(10));
    installmentDate.setTo(OffsetDateTime.now());

    PaidExportFileFilter filter = new PaidExportFileFilter();
    filter.setInstallmentUpdateDateTime(installmentDate);

    PaidExportFileRequestDTO requestDTO = new PaidExportFileRequestDTO();
    requestDTO.setFilterFields(filter);

    ExportFile<?> exportFile = new PaidExportFile();
    exportFile.setExportFileId(1L);

    String operatorExternalId = "OPERATOREXTERNALID";
    SecurityUtilsTest.configureSecurityContext(operatorExternalId);

    Mockito.doReturn(exportFile).when(serviceMock).save(Mockito.same(requestDTO), Mockito.same(operatorExternalId), Mockito.anyString());

    ResponseEntity<Void> result = controller.createPaidExportFile(requestDTO);

    assertEquals(HttpStatus.CREATED, result.getStatusCode());
    assertEquals("1", result.getHeaders().getFirst(HttpHeaders.LOCATION));
  }

  @Test
  void whenCreatePaymentsReportingExportFileThenInvokeService() {
    PaymentsReportingExportFileRequestDTO requestDTO = new PaymentsReportingExportFileRequestDTO();
    ExportFile<?> t = new PaymentsReportingExportFile();
    t.setExportFileId(1L);

    String operatorExternalId = "OPERATOREXTERNALID";
    SecurityUtilsTest.configureSecurityContext(operatorExternalId);

    Mockito.doReturn(t).when(serviceMock).save(Mockito.same(requestDTO), Mockito.same(operatorExternalId), Mockito.anyString());

    ResponseEntity<Void> result = controller.createPaymentsReportingExportFile(requestDTO);

    assertEquals(HttpStatus.CREATED, result.getStatusCode());
    assertEquals("1", result.getHeaders().getFirst(HttpHeaders.LOCATION));
  }

  @Test
  void whenCreateArchivingExportFileThenInvokeService() {
    ReceiptsArchivingExportFileRequestDTO requestDTO = new ReceiptsArchivingExportFileRequestDTO();
    ExportFile<?> t = new ReceiptsArchivingExportFile();
    t.setExportFileId(1L);

    String operatorExternalId = "OPERATOREXTERNALID";
    SecurityUtilsTest.configureSecurityContext(operatorExternalId);

    Mockito.doReturn(t).when(serviceMock).save(Mockito.same(requestDTO), Mockito.same(operatorExternalId), Mockito.anyString());

    ResponseEntity<Void> result = controller.createReceiptsArchivingExportFile(requestDTO);

    assertEquals(HttpStatus.CREATED, result.getStatusCode());
    assertEquals("1", result.getHeaders().getFirst(HttpHeaders.LOCATION));
  }

  @Test
  void whenInvalidPaymentDateRange_thenThrowInvalidTimeRangeException() {
    OffsetDateTime now = OffsetDateTime.now();
    OffsetDateTimeIntervalFilter paymentDate = new OffsetDateTimeIntervalFilter();
    paymentDate.setFrom(now.minusMonths(MAX_MONTHS_RANGE + 1));
    paymentDate.setTo(now);

    PaidExportFileFilter filter = new PaidExportFileFilter();
    filter.setPaymentDateTime(paymentDate);

    PaidExportFileRequestDTO requestDTO = new PaidExportFileRequestDTO();
    requestDTO.setFilterFields(filter);

    assertThrows(InvalidTimeRangeException.class, () -> controller.createPaidExportFile(requestDTO));
  }

  @Test
  void whenInvalidInstallmentUpdateDateRange_thenThrowInvalidTimeRangeException() {
    OffsetDateTime now = OffsetDateTime.now();
    OffsetDateTimeIntervalFilter installmentDate = new OffsetDateTimeIntervalFilter();
    installmentDate.setFrom(now.minusMonths(MAX_MONTHS_RANGE + 1));
    installmentDate.setTo(now);

    PaidExportFileFilter filter = new PaidExportFileFilter();
    filter.setInstallmentUpdateDateTime(installmentDate);

    PaidExportFileRequestDTO requestDTO = new PaidExportFileRequestDTO();
    requestDTO.setFilterFields(filter);

    assertThrows(InvalidTimeRangeException.class, () -> controller.createPaidExportFile(requestDTO));
  }

  @Test
  void whenInvalidClassificationDate_thenThrowInvalidTimeRangeException() {
    LocalDate now = LocalDate.now();
    LocalDateIntervalFilter classificationDate = new LocalDateIntervalFilter();
    classificationDate.setFrom(now.minusMonths(MAX_MONTHS_RANGE + 1));
    classificationDate.setTo(now);

    ClassificationsExportFileFilter filter = new ClassificationsExportFileFilter();
    filter.setLastClassificationDate(classificationDate);

    ClassificationsExportFileRequestDTO requestDTO = new ClassificationsExportFileRequestDTO();
    requestDTO.setFilterFields(filter);

    assertThrows(InvalidTimeRangeException.class, () -> controller.createClassificationsExportFile(requestDTO));
  }

  @Test
  void whenInvalidArchivingPaymentDate_thenThrowInvalidTimeRangeException() {
    OffsetDateTime now = OffsetDateTime.now();
    OffsetDateTimeIntervalFilter paymentDate = new OffsetDateTimeIntervalFilter();
    paymentDate.setFrom(now.minusMonths(MAX_MONTHS_RANGE + 1));
    paymentDate.setTo(now);

    ReceiptsArchivingExportFileFilter filter = new ReceiptsArchivingExportFileFilter();
    filter.setPaymentDateTime(paymentDate);

    ReceiptsArchivingExportFileRequestDTO requestDTO = new ReceiptsArchivingExportFileRequestDTO();
    requestDTO.setFilterFields(filter);

    assertThrows(InvalidTimeRangeException.class, () -> controller.createReceiptsArchivingExportFile(requestDTO));
  }

  @ParameterizedTest
  @EnumSource(ExportFileTypeEnum.class)
  void givenExportFlowFileTypeWhenThenReturnAvailableVersions(ExportFileTypeEnum exportFileType){
    Assertions.assertSame(
      ExportConstants.getAvailableVersions(exportFileType),
      controller.getExportFileTypeVersions(exportFileType).getBody()
    );
  }
}
