package it.gov.pagopa.pu.processexecutions.connector.workflowhub.client;

import it.gov.pagopa.pu.processexecutions.connector.workflowhub.config.WorkflowHubApisHolder;
import it.gov.pagopa.pu.processexecutions.enums.ExportFileTypeEnum;
import it.gov.pagopa.pu.workflowhub.client.generated.ExportFileApi;
import it.gov.pagopa.pu.workflowhub.dto.generated.WorkflowCreatedDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockitoExtension.class)
class ExportFileClientTest {
  @Mock
  private WorkflowHubApisHolder workflowHubApisHolderMock;
  @Mock
  private ExportFileApi exportFileApiMock;

  ExportFileClient exportFileClient;

  @BeforeEach
  void setUp() {
    exportFileClient = new ExportFileClient(workflowHubApisHolderMock);
  }

  @AfterEach
  void verifyNoMoreInteractions() {
    Mockito.verifyNoMoreInteractions(
      workflowHubApisHolderMock
    );
  }

  @Test
  void givenValidParamWhenExportFileThenReturnWorkflowCreatedDTO() {
    //given
    long exportFileId = 1L;
    ExportFileTypeEnum exportFileTypeEnum = ExportFileTypeEnum.PAID;
    String accessToken = "ACCESSTOKEN";
    WorkflowCreatedDTO expectedResult = new WorkflowCreatedDTO();

    Mockito.when(workflowHubApisHolderMock.getExportFileApi(accessToken)).thenReturn(exportFileApiMock);
    Mockito.when(exportFileApiMock.exportFile(exportFileId, exportFileTypeEnum)).thenReturn(expectedResult);
    //when
    WorkflowCreatedDTO result = exportFileClient.exportFile(exportFileId, exportFileTypeEnum, accessToken);
    //then
    assertSame(expectedResult, result);

  }

}
