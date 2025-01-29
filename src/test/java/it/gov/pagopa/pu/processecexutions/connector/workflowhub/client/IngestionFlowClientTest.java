package it.gov.pagopa.pu.processecexutions.connector.workflowhub.client;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

import it.gov.pagopa.pu.processecexutions.connector.workflowhub.config.WorkflowHubApisHolder;
import it.gov.pagopa.pu.workflowhub.controller.generated.IngestionFlowApi;
import it.gov.pagopa.pu.workflowhub.dto.generated.WorkflowCreatedDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

@ExtendWith(MockitoExtension.class)
class IngestionFlowClientTest {
  @Mock
  private WorkflowHubApisHolder workflowHubApisHolderMock;
  @Mock
  private IngestionFlowApi ingestionFlowApiMock;

  private IngestionFlowClient ingestionFlowClient;

  @BeforeEach
  void setUp() {
    ingestionFlowClient = new IngestionFlowClient(workflowHubApisHolderMock);
  }

  @AfterEach
  void verifyNoMoreInteractions() {
    Mockito.verifyNoMoreInteractions(
      workflowHubApisHolderMock
    );
  }

  @Test
  void whenIngestPaymentsReportingFileThenInvokeWithAccessToken() {
    long ingestionFlowFileId = 1L;
    String accessToken = "ACCESSTOKEN";
    WorkflowCreatedDTO expectedResult = new WorkflowCreatedDTO();

    when(workflowHubApisHolderMock.getIngestionFlowApi(accessToken))
      .thenReturn(ingestionFlowApiMock);
    when(ingestionFlowApiMock.ingestPaymentsReportingFile(
      ingestionFlowFileId))
      .thenReturn(expectedResult);

    WorkflowCreatedDTO result = ingestionFlowClient.ingestPaymentsReportingFile(
      ingestionFlowFileId, accessToken);

    assertSame(expectedResult, result);
  }

  @Test
  void givenGenericHttpExceptionWhenIngestPaymentsReportingFileThenThrowIt() {
    long ingestionFlowFileId = 1L;
    String accessToken = "ACCESSTOKEN";
    HttpClientErrorException expectedException = new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);

    when(workflowHubApisHolderMock.getIngestionFlowApi(accessToken))
      .thenReturn(ingestionFlowApiMock);
    when(ingestionFlowApiMock.ingestPaymentsReportingFile(
      ingestionFlowFileId))
      .thenThrow(expectedException);

    HttpClientErrorException result = Assertions.assertThrows(
      expectedException.getClass(),
      () -> ingestionFlowClient.ingestPaymentsReportingFile(
      ingestionFlowFileId, accessToken));

    Assertions.assertSame(expectedException, result);
  }

  @Test
  void givenGenericExceptionWhenIngestPaymentsReportingFileThenThrowIt() {
    long ingestionFlowFileId = 1L;
    String accessToken = "ACCESSTOKEN";
    RuntimeException expectedException = new RuntimeException();

    when(workflowHubApisHolderMock.getIngestionFlowApi(accessToken))
      .thenReturn(ingestionFlowApiMock);
    when(ingestionFlowApiMock.ingestPaymentsReportingFile(
      ingestionFlowFileId))
      .thenThrow(expectedException);

    RuntimeException result = Assertions.assertThrows(
      expectedException.getClass(),
      () -> ingestionFlowClient.ingestPaymentsReportingFile(
        ingestionFlowFileId, accessToken));

    Assertions.assertSame(expectedException, result);
  }

  @Test
  void whenIngestTreasuryOpiThenInvokeWithAccessToken() {
    long ingestionFlowFileId = 1L;
    String accessToken = "ACCESSTOKEN";
    WorkflowCreatedDTO expectedResult = new WorkflowCreatedDTO();

    when(workflowHubApisHolderMock.getIngestionFlowApi(accessToken))
      .thenReturn(ingestionFlowApiMock);
    when(ingestionFlowApiMock.ingestTreasuryOpi(
      ingestionFlowFileId))
      .thenReturn(expectedResult);

    WorkflowCreatedDTO result = ingestionFlowClient.ingestTreasuryOpi(
      ingestionFlowFileId, accessToken);

    assertSame(expectedResult, result);
  }

  @Test
  void givenGenericHttpExceptionWhenIngestTreasuryOpiThenThrowIt() {
    long ingestionFlowFileId = 1L;
    String accessToken = "ACCESSTOKEN";
    HttpClientErrorException expectedException = new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);

    when(workflowHubApisHolderMock.getIngestionFlowApi(accessToken))
      .thenReturn(ingestionFlowApiMock);
    when(ingestionFlowApiMock.ingestTreasuryOpi(
      ingestionFlowFileId))
      .thenThrow(expectedException);

    HttpClientErrorException result = Assertions.assertThrows(
      expectedException.getClass(),
      () -> ingestionFlowClient.ingestTreasuryOpi(
      ingestionFlowFileId, accessToken));

    Assertions.assertSame(expectedException, result);
  }

  @Test
  void givenGenericExceptionWhenIngestTreasuryOpiThenThrowIt() {
    long ingestionFlowFileId = 1L;
    String accessToken = "ACCESSTOKEN";
    RuntimeException expectedException = new RuntimeException();

    when(workflowHubApisHolderMock.getIngestionFlowApi(accessToken))
      .thenReturn(ingestionFlowApiMock);
    when(ingestionFlowApiMock.ingestTreasuryOpi(
      ingestionFlowFileId))
      .thenThrow(expectedException);

    RuntimeException result = Assertions.assertThrows(
      expectedException.getClass(),
      () -> ingestionFlowClient.ingestTreasuryOpi(
        ingestionFlowFileId, accessToken));

    Assertions.assertSame(expectedException, result);
  }
}
