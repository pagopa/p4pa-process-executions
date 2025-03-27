package it.gov.pagopa.pu.processecexutions.service;

import it.gov.pagopa.pu.processecexutions.dto.ExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.enums.ExportFileType;
import it.gov.pagopa.pu.processecexutions.mapper.ExportFileRequestMapper;
import it.gov.pagopa.pu.processecexutions.model.ExportFile;
import it.gov.pagopa.pu.processecexutions.model.exportfile.ClassificationsExportFile;
import it.gov.pagopa.pu.processecexutions.model.exportfile.ClassificationsExportFileFilter;
import it.gov.pagopa.pu.processecexutions.model.exportfile.PaidExportFile;
import it.gov.pagopa.pu.processecexutions.model.exportfile.PaidExportFileFilter;
import it.gov.pagopa.pu.processecexutions.model.exportfile.PaymentsReportingExportFile;
import it.gov.pagopa.pu.processecexutions.model.exportfile.PaymentsReportingExportFileFilter;
import it.gov.pagopa.pu.processecexutions.repository.exportfile.ExportFileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExportFileServiceTest {

  @Mock
  private ExportFileRequestMapper uploadedRequestMapperMock;
  @Mock
  private ExportFileRepository repositoryMock;
//  @Mock
//  TODO: mock worflow services

  private ExportFileService service;

  @BeforeEach
  void init(){
    service = new ExportFileServiceImpl(
      uploadedRequestMapperMock,
      repositoryMock);
  }

  @AfterEach
  void verifyNoMoreInteractions(){
    Mockito.verifyNoMoreInteractions(
      uploadedRequestMapperMock,
      repositoryMock
    );
  }

  @Test
  void whenSaveThenStoreAndInvokeWF_Classifications(){
    // Given
    ExportFileRequestDTO<ClassificationsExportFileFilter> requestDTO = new ExportFileRequestDTO<>();
    requestDTO.setFlowFileType(ExportFileType.CLASSIFICATIONS);
    ClassificationsExportFile newEntity = new ClassificationsExportFile();
    ClassificationsExportFile storedEntity = new ClassificationsExportFile();
    String operatorExternalId = "OPERATOREXTERNALID";
    String accessToken = "ACCESSTOKEN";

    Mockito.when(uploadedRequestMapperMock.map(Mockito.same(requestDTO), Mockito.same(operatorExternalId), Mockito.any(
        ClassificationsExportFile.class)))
      .thenReturn(newEntity);

    Mockito.when(repositoryMock.save(Mockito.same(newEntity)))
      .thenReturn(storedEntity);

    // When
    ExportFile<?> result = service.save(requestDTO, operatorExternalId,
        accessToken);

    // Then
    Assertions.assertSame(storedEntity, result);
//  TODO: verify interaction with mocked workflow service
  }

  @Test
  void whenSaveThenStoreAndInvokeWF_Paid(){
    // Given
    ExportFileRequestDTO<PaidExportFileFilter> requestDTO = new ExportFileRequestDTO<>();
    requestDTO.setFlowFileType(ExportFileType.PAID);
    PaidExportFile newEntity = new PaidExportFile();
    PaidExportFile storedEntity = new PaidExportFile();
    String operatorExternalId = "OPERATOREXTERNALID";
    String accessToken = "ACCESSTOKEN";

    Mockito.when(uploadedRequestMapperMock.map(Mockito.same(requestDTO), Mockito.same(operatorExternalId), Mockito.any(
        PaidExportFile.class)))
      .thenReturn(newEntity);

    Mockito.when(repositoryMock.save(Mockito.same(newEntity)))
      .thenReturn(storedEntity);

    // When
    ExportFile<?> result = service.save(requestDTO, operatorExternalId,
        accessToken);

    // Then
    Assertions.assertSame(storedEntity, result);

    // TODO: verify invocation to workflow service
  }

  @Test
  void whenSaveThenStoreAndInvokeWF_PaymentsReporting(){
    // Given
    ExportFileRequestDTO<PaymentsReportingExportFileFilter> requestDTO = new ExportFileRequestDTO<>();
    requestDTO.setFlowFileType(ExportFileType.PAYMENTS_REPORTING);
    PaymentsReportingExportFile newEntity = new PaymentsReportingExportFile();
    PaymentsReportingExportFile storedEntity = new PaymentsReportingExportFile();
    String operatorExternalId = "OPERATOREXTERNALID";
    String accessToken = "ACCESSTOKEN";

    Mockito.when(uploadedRequestMapperMock.map(Mockito.same(requestDTO), Mockito.same(operatorExternalId), Mockito.any(
        PaymentsReportingExportFile.class)))
      .thenReturn(newEntity);

    Mockito.when(repositoryMock.save(Mockito.same(newEntity)))
      .thenReturn(storedEntity);

    // When
    ExportFile<?> result = service.save(requestDTO, operatorExternalId,
        accessToken);

    // Then
    Assertions.assertSame(storedEntity, result);
//  TODO: verify interaction with mocked workflow service
  }
}
