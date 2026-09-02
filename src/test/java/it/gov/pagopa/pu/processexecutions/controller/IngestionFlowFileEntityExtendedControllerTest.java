package it.gov.pagopa.pu.processexecutions.controller;

import it.gov.pagopa.pu.processexecutions.dto.generated.IngestionFlowFileUpdateStatusRequestDTO;
import it.gov.pagopa.pu.processexecutions.enums.IngestionFlowFileStatus;
import it.gov.pagopa.pu.processexecutions.repository.IngestionFlowFileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@ExtendWith(MockitoExtension.class)
class IngestionFlowFileEntityExtendedControllerTest {

  @Mock
  private IngestionFlowFileRepository repositoryMock;

  private IngestionFlowFileEntityExtendedController controller;

  @BeforeEach
  void init() {
    controller = new IngestionFlowFileEntityExtendedController(repositoryMock);
  }

  @AfterEach
  void verifyNoMoreInteractions() {
    Mockito.verifyNoMoreInteractions(repositoryMock);
  }

  @Test
  void whenUpdateStatusThenInvokeRepository() {
    // Given
    long ingestionFlowFileId = 1L;
    IngestionFlowFileUpdateStatusRequestDTO requestDTO = new IngestionFlowFileUpdateStatusRequestDTO();
    requestDTO.setOldStatus(IngestionFlowFileStatus.UPLOADED);
    requestDTO.setNewStatus(IngestionFlowFileStatus.PROCESSING);
    requestDTO.setProcessedRows(10L);
    requestDTO.setTotalRows(100L);
    requestDTO.setFileVersion("FILEVERSION");
    requestDTO.setErrorDescription("ERRORDESCRIPTION");
    requestDTO.setDiscardFile("DISCARDFILENAME");
    int expectedResult = 1;

    Mockito.when(repositoryMock.updateStatus(ingestionFlowFileId, requestDTO.getFileVersion(), requestDTO.getOldStatus(), requestDTO.getNewStatus(),
        requestDTO.getProcessedRows(), requestDTO.getTotalRows(),
        requestDTO.getErrorDescription(), requestDTO.getDiscardFile()))
      .thenReturn(expectedResult);

    // When
    Integer result = controller.updateStatus(ingestionFlowFileId, requestDTO)
      .getBody();

    // Then
    Assertions.assertEquals(expectedResult, result);
  }

  @Test
  void givenZeroResultWhenUpdateStatusThenNotFound() {
    // Given
    long ingestionFlowFileId = 1L;
    IngestionFlowFileUpdateStatusRequestDTO requestDTO = new IngestionFlowFileUpdateStatusRequestDTO();
    requestDTO.setOldStatus(IngestionFlowFileStatus.UPLOADED);
    requestDTO.setNewStatus(IngestionFlowFileStatus.PROCESSING);
    requestDTO.setProcessedRows(10L);
    requestDTO.setTotalRows(100L);
    requestDTO.setFileVersion("FILEVERSION");
    requestDTO.setErrorDescription("ERRORDESCRIPTION");
    requestDTO.setDiscardFile("DISCARDFILENAME");

    Mockito.when(repositoryMock.updateStatus(ingestionFlowFileId, requestDTO.getFileVersion(), requestDTO.getOldStatus(), requestDTO.getNewStatus(),
        requestDTO.getProcessedRows(), requestDTO.getTotalRows(),
        requestDTO.getErrorDescription(), requestDTO.getDiscardFile()))
      .thenReturn(0);

    // When
    HttpStatusCode result = controller.updateStatus(ingestionFlowFileId, requestDTO)
      .getStatusCode();

    // Then
    Assertions.assertEquals(HttpStatus.NOT_FOUND, result);
  }

  @Test
  void whenUpdateFileNamesThenInvokeRepository() {
    // Given
    long ingestionFlowFileId = 1L;
    String fileName = "fileName";
    String discardFileName = "discardFileName";
    int expectedResult = 1;

    Mockito.when(repositoryMock.updateFileNames(ingestionFlowFileId, fileName, discardFileName))
      .thenReturn(expectedResult);

    // When
    Integer result = controller.updateFileNames(ingestionFlowFileId, fileName, discardFileName)
      .getBody();

    // Then
    Assertions.assertEquals(expectedResult, result);
  }

  @Test
  void whenUpdateFileNamesThenNotFound() {
    // Given
    long ingestionFlowFileId = 1L;
    String fileName = "fileName";
    String discardFileName = "discardFileName";

    Mockito.when(repositoryMock.updateFileNames(ingestionFlowFileId, fileName, discardFileName))
      .thenReturn(0);

    // When
    HttpStatusCode result = controller.updateFileNames(ingestionFlowFileId, fileName, discardFileName)
      .getStatusCode();

    // Then
    Assertions.assertEquals(HttpStatus.NOT_FOUND, result);
  }

  @Test
  void whenUpdatePdfGeneratedThenInvokeRepository() {
    // Given
    long ingestionFlowFileId = 1L;
    long pdfGenerated = 10L;
    String folderId = "100";
    int expectedResult = 1;

    Mockito.when(repositoryMock.updatePdfGeneratedAndPdfGeneratedId(ingestionFlowFileId, pdfGenerated, folderId))
      .thenReturn(expectedResult);

    // When
    Integer result = controller.updatePdfGenerated(ingestionFlowFileId, pdfGenerated, folderId)
      .getBody();

    // Then
    Assertions.assertEquals(expectedResult, result);
  }

  @Test
  void whenUpdatePdfGeneratedThenNotFound() {
    // Given
    long ingestionFlowFileId = 1L;
    long pdfGenerated = 10L;
    String folderId = "100";

    Mockito.when(repositoryMock.updatePdfGeneratedAndPdfGeneratedId(ingestionFlowFileId, pdfGenerated, folderId))
      .thenReturn(0);

    // When
    HttpStatusCode result = controller.updatePdfGenerated(ingestionFlowFileId, pdfGenerated, folderId)
      .getStatusCode();

    // Then
    Assertions.assertEquals(HttpStatus.NOT_FOUND, result);
  }
}
