package it.gov.pagopa.pu.processexecutions.controller;

import it.gov.pagopa.pu.processexecutions.controller.generated.IngestionFlowFileControllerApi;
import it.gov.pagopa.pu.processexecutions.dto.generated.IngestionFlowFileRequestDTO;
import it.gov.pagopa.pu.processexecutions.enums.IngestionFlowFileTypeEnum;
import it.gov.pagopa.pu.processexecutions.model.IngestionFlowFile;
import it.gov.pagopa.pu.processexecutions.service.IngestionFlowFileRequestService;
import it.gov.pagopa.pu.processexecutions.util.IngestionFlowFileConstants;
import it.gov.pagopa.pu.processexecutions.util.SecurityUtilsTest;
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
class IngestionFlowFileControllerApiTest {

  @Mock
  private IngestionFlowFileRequestService serviceMock;

  private IngestionFlowFileControllerApi controller;

  @BeforeEach
  void init(){
    Authentication authentication = new UsernamePasswordAuthenticationToken("fakeUser", "fakeAccessToken");
    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
    securityContext.setAuthentication(authentication);
    SecurityContextHolder.setContext(securityContext);
    controller = new IngestionFlowFileControllerImpl(serviceMock);
  }

  @AfterEach
  void verifyNoMoreInteractions(){
    Mockito.verifyNoMoreInteractions(serviceMock);
  }

  @Test
  void whenHandleUploadedThenInvokeService(){
    // Given
    IngestionFlowFileRequestDTO requestDTO = new IngestionFlowFileRequestDTO();
    IngestionFlowFile t = IngestionFlowFile.builder()
      .ingestionFlowFileId(1L)
      .build();

    String operatorExternalId = "OPERATOREXTERNALID";
    SecurityUtilsTest.configureSecurityContext(operatorExternalId);

    Mockito.when(serviceMock.handleUploaded(Mockito.same(requestDTO), Mockito.same(operatorExternalId),
            Mockito.anyString()))
      .thenReturn(t);

    // When
    ResponseEntity<Void> result = controller.createIngestionFlowFile(requestDTO);

    // Then
    Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
    Assertions.assertEquals("1", result.getHeaders().getFirst(HttpHeaders.LOCATION));
  }

  @Test
  void whenHandleReservationThenInvokeService(){
    // Given
    Long expectedResult = 1L;
    IngestionFlowFileRequestDTO requestDTO = new IngestionFlowFileRequestDTO();
    IngestionFlowFile ingestionFlowFile = IngestionFlowFile.builder()
      .ingestionFlowFileId(1L)
      .build();

    String operatorExternalId = "OPERATOREXTERNALID";
    SecurityUtilsTest.configureSecurityContext(operatorExternalId);

    Mockito.when(serviceMock.handleReservation(Mockito.same(requestDTO), Mockito.same(operatorExternalId)))
      .thenReturn(ingestionFlowFile);

    // When
    Long result = controller.createIngestionFlowFileReservation(requestDTO).getBody();

    // Then
    Assertions.assertEquals(expectedResult, result);
  }

  @ParameterizedTest
  @EnumSource(IngestionFlowFileTypeEnum.class)
  void givenExportFlowFileTypeWhenThenReturnAvailableVersions(IngestionFlowFileTypeEnum ingestionFlowFileTypeEnum){
    Assertions.assertSame(
      IngestionFlowFileConstants.getAvailableVersions(ingestionFlowFileTypeEnum),
      controller.getIngestionFlowFileVersion(ingestionFlowFileTypeEnum).getBody()
    );
  }
}
