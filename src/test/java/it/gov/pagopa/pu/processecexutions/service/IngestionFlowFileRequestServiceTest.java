package it.gov.pagopa.pu.processecexutions.service;

import it.gov.pagopa.pu.processecexutions.connector.workflowhub.IngestionFlowFileService;
import it.gov.pagopa.pu.processecexutions.dto.generated.IngestionFlowFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileStatus;
import it.gov.pagopa.pu.processecexutions.mapper.IngestionFlowFileRequestMapper;
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

@ExtendWith(MockitoExtension.class)
class IngestionFlowFileRequestServiceTest {

  @Mock
  private IngestionFlowFileRequestMapper requestMapperMock;
  @Mock
  private IngestionFlowFileRepository repositoryMock;
  @Mock
  private IngestionFlowFileService workflowInvokerServiceMock;

  private IngestionFlowFileRequestService service;

  @BeforeEach
  void init(){
    service = new IngestionFlowFileRequestServiceImpl(
      requestMapperMock,
      repositoryMock,
      workflowInvokerServiceMock);
  }

  @AfterEach
  void verifyNoMoreInteractions(){
    Mockito.verifyNoMoreInteractions(
      requestMapperMock,
      repositoryMock,
      workflowInvokerServiceMock
    );
  }

  @Test
  void whenHandleUploadedThenStoreAndInvokeWF(){
    // Given
    IngestionFlowFileRequestDTO requestDTO = new IngestionFlowFileRequestDTO();
    IngestionFlowFile newEntity = new IngestionFlowFile();
    IngestionFlowFile storedEntity = new IngestionFlowFile();
    String operatorExternalId = "OPERATOREXTERNALID";
    String accessToken = "ACCESSTOKEN";

    Mockito.when(requestMapperMock.map(Mockito.same(requestDTO), Mockito.same(operatorExternalId),
        Mockito.same(IngestionFlowFileStatus.UPLOADED)))
      .thenReturn(newEntity);

    Mockito.when(repositoryMock.save(Mockito.same(newEntity)))
      .thenReturn(storedEntity);

    // When
    IngestionFlowFile result = service.handleUploaded(requestDTO, operatorExternalId,
        accessToken);

    // Then
    Assertions.assertSame(storedEntity, result);

    Mockito.verify(workflowInvokerServiceMock)
      .invokeIngestionWorkflow(Mockito.same(storedEntity), Mockito.same(accessToken));
  }

  @Test
  void givenNotSupportedIngestionFlowFileWhenHandleUploadedThenStoreError(){
    // Given
    IngestionFlowFileRequestDTO requestDTO = new IngestionFlowFileRequestDTO();
    IngestionFlowFile newEntity = new IngestionFlowFile();
    IngestionFlowFile storedEntity = new IngestionFlowFile();
    String operatorExternalId = "OPERATOREXTERNALID";
    String accessToken = "ACCESSTOKEN";

    Mockito.when(requestMapperMock.map(Mockito.same(requestDTO), Mockito.same(operatorExternalId),
        Mockito.same(IngestionFlowFileStatus.UPLOADED)))
      .thenReturn(newEntity);

    Mockito.when(repositoryMock.save(Mockito.same(newEntity)))
      .thenReturn(storedEntity);

    Mockito.doThrow(new UnsupportedOperationException())
      .when(workflowInvokerServiceMock)
      .invokeIngestionWorkflow(Mockito.same(storedEntity), Mockito.same(accessToken));

    // When
    IngestionFlowFile result = service.handleUploaded(requestDTO, operatorExternalId,
      accessToken);

    // Then
    Assertions.assertSame(storedEntity, result);
    Assertions.assertEquals(IngestionFlowFileStatus.ERROR, result.getStatus());
    Assertions.assertEquals("Flow type not supported", result.getErrorDescription());

    Mockito.verify(repositoryMock)
      .save(Mockito.same(storedEntity));

  }

  @Test
  void givenGenericExceptionWhenHandleUploadedThenStoreError(){
    // Given
    IngestionFlowFileRequestDTO requestDTO = new IngestionFlowFileRequestDTO();
    IngestionFlowFile newEntity = new IngestionFlowFile();
    IngestionFlowFile storedEntity = new IngestionFlowFile();
    String operatorExternalId = "OPERATOREXTERNALID";
    String accessToken = "ACCESSTOKEN";

    Mockito.when(requestMapperMock.map(Mockito.same(requestDTO), Mockito.same(operatorExternalId),
        Mockito.same(IngestionFlowFileStatus.UPLOADED)))
      .thenReturn(newEntity);

    Mockito.when(repositoryMock.save(Mockito.same(newEntity)))
      .thenReturn(storedEntity);

    RuntimeException expectedException = new RuntimeException("DUMMY ERROR");
    Mockito.doThrow(expectedException)
      .when(workflowInvokerServiceMock)
      .invokeIngestionWorkflow(Mockito.same(storedEntity), Mockito.same(accessToken));

    // When
    RuntimeException result = Assertions.assertThrows(expectedException.getClass(), () ->
      service.handleUploaded(requestDTO, operatorExternalId, accessToken));

    // Then
    Assertions.assertSame(expectedException, result);
    Assertions.assertEquals(IngestionFlowFileStatus.ERROR, storedEntity.getStatus());
    Assertions.assertEquals("DUMMY ERROR", storedEntity.getErrorDescription());

    Mockito.verify(repositoryMock)
      .save(Mockito.same(storedEntity));

  }

  @Test
  void whenHandleReservationThenStore() {
    // Given
    IngestionFlowFileRequestDTO requestDTO = new IngestionFlowFileRequestDTO();
    IngestionFlowFile newEntity = new IngestionFlowFile();
    IngestionFlowFile storedEntity = new IngestionFlowFile();
    String operatorExternalId = "OPERATOREXTERNALID";

    Mockito.when(requestMapperMock.map(Mockito.same(requestDTO), Mockito.same(operatorExternalId),
        Mockito.same(IngestionFlowFileStatus.WAITING_FILE)))
      .thenReturn(newEntity);

    Mockito.when(repositoryMock.save(Mockito.same(newEntity)))
      .thenReturn(storedEntity);

    // When
    IngestionFlowFile result = service.handleReservation(requestDTO, operatorExternalId);

    // Then
    Assertions.assertSame(storedEntity, result);
  }
}
