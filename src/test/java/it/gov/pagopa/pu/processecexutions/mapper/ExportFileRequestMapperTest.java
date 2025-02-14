package it.gov.pagopa.pu.processecexutions.mapper;

import it.gov.pagopa.pu.processecexutions.dto.generated.ExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.enums.ExportFileStatus;
import it.gov.pagopa.pu.processecexutions.enums.ExportFlowFileType;
import it.gov.pagopa.pu.processecexutions.model.ExportFile;
import it.gov.pagopa.pu.processecexutions.model.exportfile.PaymentsReportingExportFile;
import it.gov.pagopa.pu.processecexutions.util.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExportFileRequestMapperTest {

  private final ExportFileRequestMapper mapper = new ExportFileRequestMapper();

  @Test
  void test() {
    // Given
    ExportFileRequestDTO dto = ExportFileRequestDTO.builder()
      .organizationId(0L)
      .filePathName("FILEPATHNAME")
      .fileName("FILENAME")
      .fileSize(1L)
      .flowFileType(ExportFileRequestDTO.FlowFileTypeEnum.PAYMENTS_REPORTING)
      .build();

    // When
    ExportFile<?> result = mapper.map(dto, "OPERATOREXTERNALID", new PaymentsReportingExportFile());

    // Then
    Assertions.assertNotNull(result);

    Assertions.assertEquals(0L, result.getOrganizationId());
    Assertions.assertEquals("OPERATOREXTERNALID", result.getOperatorExternalId());
    Assertions.assertEquals("FILEPATHNAME", result.getFilePathName());
    Assertions.assertEquals("FILENAME", result.getFileName());
    Assertions.assertEquals(1L, result.getFileSize());
    Assertions.assertEquals(ExportFlowFileType.PAYMENTS_REPORTING, result.getFlowFileType());
    Assertions.assertEquals(ExportFileStatus.REQUESTED, result.getStatus());

    TestUtils.checkNotNullFields(result,
      "exportFileId",
      "filterFields",
      "errorDescription",
      "numTotalRows",
      "pdfGenerated",
      "creationDate",
      "updateDate",
      "expirationDate",
      "updateOperatorExternalId"
    );
  }
}
