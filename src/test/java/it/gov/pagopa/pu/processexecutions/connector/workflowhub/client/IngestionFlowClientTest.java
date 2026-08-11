package it.gov.pagopa.pu.processexecutions.connector.workflowhub.client;

import it.gov.pagopa.pu.processexecutions.connector.workflowhub.config.WorkflowHubApisHolder;
import it.gov.pagopa.pu.processexecutions.enums.IngestionFlowFileTypeEnum;
import it.gov.pagopa.pu.processexecutions.exception.common.InvalidValueException;
import it.gov.pagopa.pu.processexecutions.exception.common.RestInvokeInvalidValueException;
import it.gov.pagopa.pu.workflowhub.client.generated.IngestionFlowApi;
import it.gov.pagopa.pu.workflowhub.dto.generated.CategoryEnum;
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

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

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
  void whenIngestFlowFileThenInvokeWithAccessToken() {
    long ingestionFlowFileId = 1L;
    IngestionFlowFileTypeEnum ingestionFlowFileType = IngestionFlowFileTypeEnum.PAYMENTS_REPORTING;
    String accessToken = "ACCESSTOKEN";
    WorkflowCreatedDTO expectedResult = new WorkflowCreatedDTO();

    when(workflowHubApisHolderMock.getIngestionFlowApi(accessToken))
      .thenReturn(ingestionFlowApiMock);
    when(ingestionFlowApiMock.ingestFlowFile(ingestionFlowFileId, ingestionFlowFileType))
      .thenReturn(expectedResult);

    WorkflowCreatedDTO result = ingestionFlowClient.ingestFlowFile(
      ingestionFlowFileId, ingestionFlowFileType, accessToken);

    assertSame(expectedResult, result);
  }

  @Test
  void givenNotSupportedIngestionFlowFileWhenIngestFlowFileThenThrowUnsupportedOperationException() {
    long ingestionFlowFileId = 1L;
    IngestionFlowFileTypeEnum ingestionFlowFileType = IngestionFlowFileTypeEnum.PAYMENTS_REPORTING;
    String accessToken = "ACCESSTOKEN";

    when(workflowHubApisHolderMock.getIngestionFlowApi(accessToken))
      .thenReturn(ingestionFlowApiMock);
    when(ingestionFlowApiMock.ingestFlowFile(ingestionFlowFileId, ingestionFlowFileType))
      .thenThrow(new RestInvokeInvalidValueException("APPNAME", HttpStatus.BAD_REQUEST,
        CategoryEnum.WORKFLOW_INGESTION_FLOW_FILE_NOT_SUPPORTED.getValue(),
        "WORKFLOW_INGESTION_FLOW_FILE_NOT_SUPPORTED", "ERRORMESSAGE", null));

    Assertions.assertThrows(InvalidValueException.class, () -> ingestionFlowClient.ingestFlowFile(
      ingestionFlowFileId, ingestionFlowFileType, accessToken));
  }

  @Test
  void givenGenericBadRequestWhenIngestFlowFileThenThrowNotSupportedException() {
    long ingestionFlowFileId = 1L;
    IngestionFlowFileTypeEnum ingestionFlowFileType = IngestionFlowFileTypeEnum.PAYMENTS_REPORTING;
    String accessToken = "ACCESSTOKEN";
    HttpClientErrorException expectedException = HttpClientErrorException.create(HttpStatus.BAD_REQUEST, "Bad request", null, null, null);

    when(workflowHubApisHolderMock.getIngestionFlowApi(accessToken))
      .thenReturn(ingestionFlowApiMock);
    when(ingestionFlowApiMock.ingestFlowFile(ingestionFlowFileId, ingestionFlowFileType))
      .thenThrow(expectedException);

    HttpClientErrorException.BadRequest result = Assertions.assertThrows(HttpClientErrorException.BadRequest.class, () -> ingestionFlowClient.ingestFlowFile(
      ingestionFlowFileId, ingestionFlowFileType, accessToken));

    Assertions.assertSame(expectedException, result);
  }

}
