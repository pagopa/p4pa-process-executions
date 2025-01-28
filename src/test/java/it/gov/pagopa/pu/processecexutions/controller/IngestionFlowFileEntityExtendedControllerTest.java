package it.gov.pagopa.pu.processecexutions.controller;

import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileStatus;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileType;
import it.gov.pagopa.pu.processecexutions.model.IngestionFlowFile;
import it.gov.pagopa.pu.processecexutions.repository.IngestionFlowFileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

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
    IngestionFlowFileStatus status = IngestionFlowFileStatus.PROCESSING;
    int expectedResult = 1;

    Mockito.when(repositoryMock.updateStatus(ingestionFlowFileId, status, codError, discardFilename))
      .thenReturn(expectedResult);

    // When
    int result = controller.updateStatus(ingestionFlowFileId, status, codError, discardFilename);

    // Then
    Assertions.assertEquals(expectedResult, result);
  }
}
