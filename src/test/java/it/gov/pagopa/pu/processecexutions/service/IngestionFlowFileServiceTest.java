package it.gov.pagopa.pu.processecexutions.service;

import it.gov.pagopa.pu.processecexutions.dto.generated.IngestionFlowFileRequestDTO;
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
class IngestionFlowFileServiceTest {

  @Mock
  private IngestionFlowFileRequestMapper uploadedRequestMapperMock;
  @Mock
  private IngestionFlowFileRepository repositoryMock;
  @Mock
  private IngestionFlowFileWorkflowInvokerService workflowInvokerServiceMock;

  private IngestionFlowFileService service;

  @BeforeEach
  void init(){
    service = new IngestionFlowFileServiceImpl(
      uploadedRequestMapperMock,
      repositoryMock,
      workflowInvokerServiceMock);
  }

  @AfterEach
  void verifyNoMoreInteractions(){
    Mockito.verifyNoMoreInteractions(
      uploadedRequestMapperMock,
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

    Mockito.when(uploadedRequestMapperMock.map(Mockito.same(requestDTO), Mockito.same(operatorExternalId)))
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
}
