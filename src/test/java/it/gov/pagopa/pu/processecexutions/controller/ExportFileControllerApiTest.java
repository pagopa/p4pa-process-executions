package it.gov.pagopa.pu.processecexutions.controller;

import it.gov.pagopa.pu.processecexutions.controller.generated.ExportFileControllerApi;
import it.gov.pagopa.pu.processecexutions.dto.exportFile.ReceiptsArchivingExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.dto.exportFile.ClassificationsExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.dto.exportFile.PaidExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.dto.exportFile.PaymentsReportingExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.enums.ExportFileType;
import it.gov.pagopa.pu.processecexutions.model.ExportFile;
import it.gov.pagopa.pu.processecexutions.model.exportfile.ReceiptsArchivingExportFile;
import it.gov.pagopa.pu.processecexutions.model.exportfile.ClassificationsExportFile;
import it.gov.pagopa.pu.processecexutions.model.exportfile.PaidExportFile;
import it.gov.pagopa.pu.processecexutions.model.exportfile.PaymentsReportingExportFile;
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

@ExtendWith(MockitoExtension.class)
class ExportFileControllerApiTest {

  @Mock
  private ExportFileSaveService serviceMock;

  private ExportFileControllerApi controller;

  @BeforeEach
  void init(){
    Authentication authentication = new UsernamePasswordAuthenticationToken("fakeUser", "fakeAccessToken");
    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
    securityContext.setAuthentication(authentication);
    SecurityContextHolder.setContext(securityContext);
    controller = new ExportFileControllerImpl(serviceMock);
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
  @EnumSource(ExportFileType.class)
  void givenExportFlowFileTypeWhenThenReturnAvailableVersions(ExportFileType exportFileType){
    Assertions.assertSame(
      ExportConstants.getAvailableVersions(exportFileType),
      controller.getExportFileTypeVersions(exportFileType).getBody()
    );
  }
}
