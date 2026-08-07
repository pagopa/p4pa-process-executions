package it.gov.pagopa.pu.processexecutions.connector.workflowhub;

import it.gov.pagopa.pu.processexecutions.connector.workflowhub.client.ExportFileClient;
import it.gov.pagopa.pu.processexecutions.enums.ExportFileTypeEnum;
import it.gov.pagopa.pu.processexecutions.model.exportfile.PaidExportFile;
import it.gov.pagopa.pu.workflowhub.dto.generated.WorkflowCreatedDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExportFileServiceImplTest {

  @Mock
  private ExportFileClient exportFileClientMock;

  ExportFileService exportFileService;

  @BeforeEach
  void setUp() {
    exportFileService = new ExportFileServiceImpl(exportFileClientMock);
  }

  @AfterEach
  void verifyNoMoreInteractions() {
    Mockito.verifyNoMoreInteractions(
      exportFileClientMock
    );
  }

  @Test
  void givenValidParamWhenInvokeExportFileWorkflowThenOk() {
    //given
    PaidExportFile paidExportFile = new PaidExportFile();
    paidExportFile.setExportFileId(1L);
    paidExportFile.setExportFileType(ExportFileTypeEnum.PAID);
    String accessToken = "ACCESSTOKEN";

    WorkflowCreatedDTO workflowCreatedDTO = new WorkflowCreatedDTO();

    Mockito.when(exportFileClientMock.exportFile(1L, ExportFileTypeEnum.PAID, accessToken)).thenReturn(workflowCreatedDTO);
    //when
    exportFileService.invokeExportFileWorkflow(paidExportFile, accessToken);
    //then

    Mockito.verify(exportFileClientMock).exportFile(paidExportFile.getExportFileId(), ExportFileTypeEnum.PAID, accessToken);

  }
}
