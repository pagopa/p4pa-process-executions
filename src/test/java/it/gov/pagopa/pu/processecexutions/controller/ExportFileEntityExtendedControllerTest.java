package it.gov.pagopa.pu.processecexutions.controller;

import it.gov.pagopa.pu.processecexutions.enums.ExportFileStatus;
import it.gov.pagopa.pu.processecexutions.repository.exportfile.ExportFileRepository;
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

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class ExportFileEntityExtendedControllerTest {

  @Mock
  private ExportFileRepository repositoryMock;

  private ExportFileEntityExtendedController controller;

  @BeforeEach
  void init(){
    controller = new ExportFileEntityExtendedController(repositoryMock);
  }

  @AfterEach
  void verifyNoMoreInteractions(){
    Mockito.verifyNoMoreInteractions(repositoryMock);
  }

  @Test
  void whenUpdateStatusThenInvokeRepository(){
    // Given
    long exportFileId = 1L;
    String codError = "CODERROR";
    ExportFileStatus oldStatus = ExportFileStatus.COMPLETED;
    ExportFileStatus newStatus = ExportFileStatus.EXPIRED;
    String filePathName = "filePath";
    String fileName = "fileName";
    Long numTotalRows = 2L;
    Long fileSize = 3L;
    LocalDate expirationDate = LocalDate.now().plusDays(5L);
    int expectedResult = 1;

    Mockito.when(repositoryMock.updateStatus(exportFileId, oldStatus, newStatus, filePathName, fileName, fileSize, numTotalRows, codError, expirationDate))
      .thenReturn(expectedResult);

    // When
    Integer result = controller.updateExportFileStatus(exportFileId, oldStatus, newStatus, filePathName, fileName, fileSize, numTotalRows, codError, expirationDate).getBody();

    // Then
    Assertions.assertEquals(expectedResult, result);
  }

  @Test
  void givenZeroResultWhenUpdateStatusThenNotFound(){
    // Given
    long exportFileId = 1L;
    String codError = "CODERROR";
    ExportFileStatus oldStatus = ExportFileStatus.COMPLETED;
    ExportFileStatus newStatus = ExportFileStatus.EXPIRED;
    String filePathName = "filePath";
    String fileName = "fileName";
    Long numTotalRows = 2L;
    Long fileSize = 3L;
    LocalDate expirationDate = LocalDate.now().plusDays(5L);

    Mockito.when(repositoryMock.updateStatus(exportFileId, oldStatus, newStatus, filePathName, fileName, fileSize, numTotalRows, codError, expirationDate))
      .thenReturn(0);

    // When
    HttpStatusCode result = controller.updateExportFileStatus(exportFileId, oldStatus, newStatus, filePathName, fileName, fileSize, numTotalRows, codError, expirationDate).getStatusCode();

    // Then
    Assertions.assertEquals(HttpStatus.NOT_FOUND, result);
  }
}
