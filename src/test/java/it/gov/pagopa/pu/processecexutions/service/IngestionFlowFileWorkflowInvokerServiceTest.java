package it.gov.pagopa.pu.processecexutions.service;

import it.gov.pagopa.pu.processecexutions.connector.workflowhub.client.IngestionFlowClient;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileType;
import it.gov.pagopa.pu.processecexutions.model.IngestionFlowFile;
import it.gov.pagopa.pu.workflowhub.dto.generated.WorkflowCreatedDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IngestionFlowFileWorkflowInvokerServiceTest {
  @Mock
  private IngestionFlowClient ingestionFlowClientMock;

  private IngestionFlowFileWorkflowInvokerService service;

  @BeforeEach
  void init(){
    service = new IngestionFlowFileWorkflowInvokerService(
      ingestionFlowClientMock);
  }

  @AfterEach
  void verifyNoMoreInteractions(){
    Mockito.verifyNoMoreInteractions(
      ingestionFlowClientMock
    );
  }

  @Test
  void givenPaymentsReportingWhenInvokeIngestionWorkflowThenInvokeClient(){
    // Given
    IngestionFlowFile ingestionFlowFile = new IngestionFlowFile();
    ingestionFlowFile.setIngestionFlowFileId(1L);
    ingestionFlowFile.setFlowFileType(IngestionFlowFileType.PAYMENTS_REPORTING);
    String accessToken = "ACCESSTOKEN";
    WorkflowCreatedDTO workflowCreatedDTO = new WorkflowCreatedDTO();

    Mockito.when(ingestionFlowClientMock.ingestPaymentsReportingFile(ingestionFlowFile.getIngestionFlowFileId(),accessToken))
      .thenReturn(workflowCreatedDTO);

    service.invokeIngestionWorkflow(ingestionFlowFile,accessToken);

    Mockito.verify(ingestionFlowClientMock)
      .ingestPaymentsReportingFile(ingestionFlowFile.getIngestionFlowFileId(),accessToken);
  }

  @Test
  void givenTreasuryOpiWhenInvokeIngestionWorkflowThenInvokeClient(){
    // Given
    IngestionFlowFile ingestionFlowFile = new IngestionFlowFile();
    ingestionFlowFile.setIngestionFlowFileId(1L);
    ingestionFlowFile.setFlowFileType(IngestionFlowFileType.TREASURY_OPI);
    String accessToken = "ACCESSTOKEN";
    WorkflowCreatedDTO workflowCreatedDTO = new WorkflowCreatedDTO();

    Mockito.when(ingestionFlowClientMock.ingestTreasuryOpi(ingestionFlowFile.getIngestionFlowFileId(),accessToken))
      .thenReturn(workflowCreatedDTO);

    service.invokeIngestionWorkflow(ingestionFlowFile,accessToken);

    Mockito.verify(ingestionFlowClientMock)
      .ingestTreasuryOpi(ingestionFlowFile.getIngestionFlowFileId(),accessToken);
  }

  @Test
  void givenUnsupportedIngestionFlowFileTypeWhenInvokeIngestionWorkflowThenUnsupportedOperation(){
    // Given
    IngestionFlowFile ingestionFlowFile = new IngestionFlowFile();
    ingestionFlowFile.setIngestionFlowFileId(1L);
    ingestionFlowFile.setFlowFileType(IngestionFlowFileType.PAYMENTS_REPORTING_PAGOPA);
    String accessToken = "ACCESSTOKEN";

    Assertions.assertThrows(
      UnsupportedOperationException.class,
      () -> service.invokeIngestionWorkflow(ingestionFlowFile, accessToken)
    );
  }
}
