package it.gov.pagopa.pu.processecexutions.service;

import it.gov.pagopa.pu.processecexutions.connector.workflowhub.ExportFileService;
import it.gov.pagopa.pu.processecexutions.dto.exportFile.*;
import it.gov.pagopa.pu.processecexutions.enums.ExportFileType;
import it.gov.pagopa.pu.processecexutions.mapper.ExportFileRequestMapper;
import it.gov.pagopa.pu.processecexutions.model.ExportFile;
import it.gov.pagopa.pu.processecexutions.model.exportfile.*;
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
class ExportFileSaveServiceTest {

  @Mock
  private ExportFileRequestMapper uploadedRequestMapperMock;
  @Mock
  private ExportFileRepository repositoryMock;
  @Mock
  private ExportFileService exportFileServiceMock;

  private ExportFileSaveService service;

  @BeforeEach
  void init(){
    service = new ExportFileSaveServiceImpl(
      uploadedRequestMapperMock,
      repositoryMock, exportFileServiceMock);
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
    ExportFileRequestDTO<ClassificationsExportFileFilter> requestDTO = new ClassificationsExportFileRequestDTO();
    requestDTO.setExportFileType(ExportFileType.CLASSIFICATIONS);
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
    Mockito.verify(exportFileServiceMock).invokeExportFileWorkflow(result, accessToken);
  }

  @Test
  void whenSaveThenStoreAndInvokeWF_Paid(){
    // Given
    ExportFileRequestDTO<PaidExportFileFilter> requestDTO = new PaidExportFileRequestDTO();
    requestDTO.setExportFileType(ExportFileType.PAID);
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
    Mockito.verify(exportFileServiceMock).invokeExportFileWorkflow(result, accessToken);
  }

  @Test
  void whenSaveThenStoreAndInvokeWF_PaymentsReporting(){
    // Given
    ExportFileRequestDTO<PaymentsReportingExportFileFilter> requestDTO = new PaymentsReportingExportFileRequestDTO();
    requestDTO.setExportFileType(ExportFileType.PAYMENTS_REPORTING);
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
    Mockito.verify(exportFileServiceMock).invokeExportFileWorkflow(result, accessToken);
  }

  @Test
  void whenSaveThenStoreAndInvokeWF_Archiving(){
    // Given
    ExportFileRequestDTO<ReceiptsArchivingExportFileFilter> requestDTO = new ReceiptsArchivingExportFileRequestDTO();
    requestDTO.setExportFileType(ExportFileType.RECEIPTS_ARCHIVING);
    ReceiptsArchivingExportFile newEntity = new ReceiptsArchivingExportFile();
    ReceiptsArchivingExportFile storedEntity = new ReceiptsArchivingExportFile();

    String operatorExternalId = "OPERATOREXTERNALID";
    String accessToken = "ACCESSTOKEN";

    Mockito.when(uploadedRequestMapperMock.map(Mockito.same(requestDTO), Mockito.same(operatorExternalId), Mockito.any(
        ReceiptsArchivingExportFile.class)))
      .thenReturn(newEntity);

    Mockito.when(repositoryMock.save(Mockito.same(newEntity)))
      .thenReturn(storedEntity);

    // When
    ExportFile<?> result = service.save(requestDTO, operatorExternalId,
      accessToken);

    // Then
    Assertions.assertSame(storedEntity, result);
    Mockito.verify(exportFileServiceMock).invokeExportFileWorkflow(result, accessToken);
  }
}
