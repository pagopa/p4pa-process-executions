package it.gov.pagopa.pu.processecexutions.controller;

import it.gov.pagopa.pu.processecexutions.controller.generated.ExportFileControllerApi;
import it.gov.pagopa.pu.processecexutions.dto.OffsetDateTimeIntervalFilter;
import it.gov.pagopa.pu.processecexutions.dto.exportFile.ReceiptsArchivingExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.dto.exportFile.ClassificationsExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.dto.exportFile.PaidExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.dto.exportFile.PaymentsReportingExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.enums.ExportFileTypeEnum;
import it.gov.pagopa.pu.processecexutions.exception.InvalidParamException;
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

import java.time.OffsetDateTime;

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
  void whenCreateClassificationsExportFileThenInvokeService(){
    // Given
    ClassificationsExportFileRequestDTO requestDTO = new ClassificationsExportFileRequestDTO();
    ExportFile<?> t = new ClassificationsExportFile();
    t.setExportFileId(1L);

    String operatorExternalId = "OPERATOREXTERNALID";
    SecurityUtilsTest.configureSecurityContext(operatorExternalId);

    Mockito.doReturn(t).when(serviceMock).save(Mockito.same(requestDTO), Mockito.same(operatorExternalId),
      Mockito.anyString());

    // When
    ResponseEntity<Void> result = controller.createClassificationsExportFile(requestDTO);

    // Then
    Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    Assertions.assertEquals("1", result.getHeaders().getFirst(HttpHeaders.LOCATION));
  }

  @Test
  void whenCreatePaidExportFileThenInvokeService(){
    // Given
    PaidExportFileRequestDTO requestDTO = new PaidExportFileRequestDTO();
    ExportFile<?> t = new PaidExportFile();
    t.setExportFileId(1L);

    String operatorExternalId = "OPERATOREXTERNALID";
    SecurityUtilsTest.configureSecurityContext(operatorExternalId);

    Mockito.doReturn(t).when(serviceMock).save(Mockito.same(requestDTO), Mockito.same(operatorExternalId),
      Mockito.anyString());

    // When
    ResponseEntity<Void> result = controller.createPaidExportFile(requestDTO);

    // Then
    Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    Assertions.assertEquals("1", result.getHeaders().getFirst(HttpHeaders.LOCATION));
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

    Assertions.assertThrows(InvalidParamException.class, () ->
      controller.createPaidExportFile(requestDTO));
  }

  @Test
  void whenNoDateRangesProvided_thenThrowInvalidDatesException() {
    PaidExportFileFilter filter = new PaidExportFileFilter(); // no dates

    PaidExportFileRequestDTO requestDTO = new PaidExportFileRequestDTO();
    requestDTO.setFilterFields(filter);

    Assertions.assertThrows(InvalidParamException.class, () ->
      controller.createPaidExportFile(requestDTO));
  }

  @Test
  void whenOnlyPaymentDateProvided_thenInvokeService() {
    OffsetDateTimeIntervalFilter paymentDate = new OffsetDateTimeIntervalFilter();
    paymentDate.setFrom(OffsetDateTime.now().minusDays(5));
    paymentDate.setTo(OffsetDateTime.now());

    PaidExportFileFilter filter = new PaidExportFileFilter();
    filter.setPaymentDateTime(paymentDate);
    filter.setInstallmentUpdateDateTime(null);

    PaidExportFileRequestDTO requestDTO = new PaidExportFileRequestDTO();
    requestDTO.setFilterFields(filter);

    ExportFile<?> exportFile = new PaidExportFile();
    exportFile.setExportFileId(1L);

    String operatorExternalId = "OPERATOREXTERNALID";
    SecurityUtilsTest.configureSecurityContext(operatorExternalId);

    Mockito.doReturn(exportFile).when(serviceMock).save(Mockito.same(requestDTO), Mockito.same(operatorExternalId), Mockito.anyString());

    ResponseEntity<Void> result = controller.createPaidExportFile(requestDTO);

    Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    Assertions.assertEquals("1", result.getHeaders().getFirst(HttpHeaders.LOCATION));
  }

  @Test
  void whenOnlyInstallmentUpdateDateProvided_thenInvokeService() {
    OffsetDateTimeIntervalFilter installmentDate = new OffsetDateTimeIntervalFilter();
    installmentDate.setFrom(OffsetDateTime.now().minusDays(10));
    installmentDate.setTo(OffsetDateTime.now());

    PaidExportFileFilter filter = new PaidExportFileFilter();
    filter.setInstallmentUpdateDateTime(installmentDate);
    filter.setPaymentDateTime(null);

    PaidExportFileRequestDTO requestDTO = new PaidExportFileRequestDTO();
    requestDTO.setFilterFields(filter);

    ExportFile<?> exportFile = new PaidExportFile();
    exportFile.setExportFileId(1L);

    String operatorExternalId = "OPERATOREXTERNALID";
    SecurityUtilsTest.configureSecurityContext(operatorExternalId);

    Mockito.doReturn(exportFile).when(serviceMock).save(Mockito.same(requestDTO), Mockito.same(operatorExternalId), Mockito.anyString());

    ResponseEntity<Void> result = controller.createPaidExportFile(requestDTO);

    Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    Assertions.assertEquals("1", result.getHeaders().getFirst(HttpHeaders.LOCATION));
  }

  @Test
  void whenCreatePaymentsReportingExportFileThenInvokeService(){
    // Given
    PaymentsReportingExportFileRequestDTO requestDTO = new PaymentsReportingExportFileRequestDTO();
    ExportFile<?> t = new PaymentsReportingExportFile();
    t.setExportFileId(1L);

    String operatorExternalId = "OPERATOREXTERNALID";
    SecurityUtilsTest.configureSecurityContext(operatorExternalId);

    Mockito.doReturn(t).when(serviceMock).save(Mockito.same(requestDTO), Mockito.same(operatorExternalId),
      Mockito.anyString());

    // When
    ResponseEntity<Void> result = controller.createPaymentsReportingExportFile(requestDTO);

    // Then
    Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    Assertions.assertEquals("1", result.getHeaders().getFirst(HttpHeaders.LOCATION));
  }

  @Test
  void whenCreateArchivingExportFileThenInvokeService(){
    // Given
    ReceiptsArchivingExportFileRequestDTO receiptsArchivingExportFileRequestDTO = new ReceiptsArchivingExportFileRequestDTO();
    ExportFile<?> t = new ReceiptsArchivingExportFile();
    t.setExportFileId(1L);

    String operatorExternalId = "OPERATOREXTERNALID";
    SecurityUtilsTest.configureSecurityContext(operatorExternalId);

    Mockito.doReturn(t).when(serviceMock).save(Mockito.same(receiptsArchivingExportFileRequestDTO), Mockito.same(operatorExternalId),
      Mockito.anyString());

    // When
    ResponseEntity<Void> result = controller.createReceiptsArchivingExportFile(receiptsArchivingExportFileRequestDTO);

    // Then
    Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    Assertions.assertEquals("1", result.getHeaders().getFirst(HttpHeaders.LOCATION));
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
