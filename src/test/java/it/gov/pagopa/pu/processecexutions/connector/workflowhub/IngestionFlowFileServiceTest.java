package it.gov.pagopa.pu.processecexutions.connector.workflowhub;

import it.gov.pagopa.pu.processecexutions.connector.workflowhub.client.IngestionFlowClient;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileType;
import it.gov.pagopa.pu.processecexutions.model.IngestionFlowFile;
import it.gov.pagopa.pu.workflowhub.dto.generated.WorkflowCreatedDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IngestionFlowFileServiceTest {
  @Mock
  private IngestionFlowClient ingestionFlowClientMock;

  private IngestionFlowFileService service;

  @BeforeEach
  void init(){
    service = new IngestionFlowFileServiceImpl(ingestionFlowClientMock);
  }

  @AfterEach
  void verifyNoMoreInteractions(){
    Mockito.verifyNoMoreInteractions(
      ingestionFlowClientMock
    );
  }

  @Test
  void whenIngestFlowFileThenInvokeClient(){
    // Given
    IngestionFlowFile ingestionFlowFile = new IngestionFlowFile();
    ingestionFlowFile.setIngestionFlowFileId(1L);
    ingestionFlowFile.setIngestionFlowFileType(IngestionFlowFileType.PAYMENTS_REPORTING);
    String accessToken = "ACCESSTOKEN";
    WorkflowCreatedDTO workflowCreatedDTO = new WorkflowCreatedDTO();

    Mockito.when(ingestionFlowClientMock.ingestFlowFile(ingestionFlowFile.getIngestionFlowFileId(), ingestionFlowFile.getIngestionFlowFileType(), accessToken))
      .thenReturn(workflowCreatedDTO);

    service.invokeIngestionWorkflow(ingestionFlowFile,accessToken);

    Mockito.verify(ingestionFlowClientMock)
      .ingestFlowFile(ingestionFlowFile.getIngestionFlowFileId(), ingestionFlowFile.getIngestionFlowFileType(), accessToken);
  }
}
