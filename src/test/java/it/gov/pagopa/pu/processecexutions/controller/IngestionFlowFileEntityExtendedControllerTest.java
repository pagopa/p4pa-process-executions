package it.gov.pagopa.pu.processecexutions.controller;

import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileStatus;
import it.gov.pagopa.pu.processecexutions.repository.IngestionFlowFileRepository;
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
  void init(){
    controller = new IngestionFlowFileEntityExtendedController(repositoryMock);
  }

  @AfterEach
  void verifyNoMoreInteractions(){
    Mockito.verifyNoMoreInteractions(repositoryMock);
  }

  @Test
  void whenUpdateStatusThenInvokeRepository(){
    // Given
    long ingestionFlowFileId = 1L;
    String codError = "CODERROR";
    String discardFilename = "DISCARDFILENAME";
    IngestionFlowFileStatus oldStatus = IngestionFlowFileStatus.UPLOADED;
    IngestionFlowFileStatus newStatus = IngestionFlowFileStatus.PROCESSING;
    int expectedResult = 1;

    Mockito.when(repositoryMock.updateStatus(ingestionFlowFileId, oldStatus, newStatus, codError, discardFilename))
      .thenReturn(expectedResult);

    // When
    Integer result = controller.updateStatus(ingestionFlowFileId, oldStatus, newStatus, codError, discardFilename).getBody();

    // Then
    Assertions.assertEquals(expectedResult, result);
  }

  @Test
  void givenZeroResultWhenUpdateStatusThenNotFound(){
    // Given
    long ingestionFlowFileId = 1L;
    String codError = "CODERROR";
    String discardFilename = "DISCARDFILENAME";
    IngestionFlowFileStatus oldStatus = IngestionFlowFileStatus.UPLOADED;
    IngestionFlowFileStatus newStatus = IngestionFlowFileStatus.PROCESSING;

    Mockito.when(repositoryMock.updateStatus(ingestionFlowFileId, oldStatus, newStatus, codError, discardFilename))
      .thenReturn(0);

    // When
    HttpStatusCode result = controller.updateStatus(ingestionFlowFileId, oldStatus, newStatus, codError, discardFilename).getStatusCode();

    // Then
    Assertions.assertEquals(HttpStatus.NOT_FOUND, result);
  }
}
