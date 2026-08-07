package it.gov.pagopa.pu.processexecutions.mapper;

import it.gov.pagopa.pu.processexecutions.dto.exportFile.ExportFileRequestDTO;
import it.gov.pagopa.pu.processexecutions.dto.exportFile.PaidExportFileRequestDTO;
import it.gov.pagopa.pu.processexecutions.enums.ExportFileStatus;
import it.gov.pagopa.pu.processexecutions.enums.ExportFileTypeEnum;
import it.gov.pagopa.pu.processexecutions.exception.ExportFlowFileVersionNotSupportedException;
import it.gov.pagopa.pu.processexecutions.model.exportfile.PaidExportFile;
import it.gov.pagopa.pu.processexecutions.model.exportfile.PaidExportFileFilter;
import it.gov.pagopa.pu.processexecutions.model.exportfile.PaidExportFileVersion;
import it.gov.pagopa.pu.processexecutions.util.ExportConstants;
import it.gov.pagopa.pu.processexecutions.util.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExportFileRequestMapperTest {

  private final ExportFileRequestMapper mapper = new ExportFileRequestMapper();

  @Test
  void whenMapThenOk() {
    // Given
    PaidExportFileRequestDTO dto = PaidExportFileRequestDTO.builder()
      .organizationId(0L)
      .exportFileType(ExportFileTypeEnum.PAID)
      .fileVersion("v1.0")
      .filterFields(new PaidExportFileFilter())
      .build();

    // When
    PaidExportFile result = mapper.map(dto, "OPERATOREXTERNALID", new PaidExportFile());

    // Then
    Assertions.assertNotNull(result);

    Assertions.assertEquals(0L, result.getOrganizationId());
    Assertions.assertEquals("OPERATOREXTERNALID", result.getOperatorExternalId());
    Assertions.assertEquals(ExportFileTypeEnum.PAID, result.getExportFileType());
    Assertions.assertEquals(PaidExportFileVersion.V1.toString(), result.getFileVersion());
    Assertions.assertEquals(ExportFileStatus.REQUESTED, result.getStatus());

    TestUtils.checkNotNullFields(result,
      "exportFileId",
      "filePathName",
      "fileSize",
      "fileName",
      "errorDescription",
      "numTotalRows",
      "creationDate",
      "updateDate",
      "expirationDate",
      "updateOperatorExternalId",
      "updateTraceId"
    );
  }

  @Test
  void givenUnsupportedVersionWhenMapThen (){
    // Given
    ExportFileRequestDTO<PaidExportFileFilter> dto = PaidExportFileRequestDTO.builder()
      .organizationId(0L)
      .exportFileType(ExportFileTypeEnum.PAID)
      .fileVersion("NOTVALID")
      .filterFields(new PaidExportFileFilter())
      .build();
    PaidExportFile exportFile = new PaidExportFile();

    // When
    ExportFlowFileVersionNotSupportedException result = Assertions.assertThrows(ExportFlowFileVersionNotSupportedException.class, () -> mapper.map(dto, "OPERATOREXTERNALID", exportFile));

    // Then
    Assertions.assertEquals("INVALID_FILE_VERSION", result.getCode());
    Assertions.assertEquals("File version NOTVALID not supported for PAID: Available versions are: " + ExportConstants.getAvailableVersions(ExportFileTypeEnum.PAID),
      result.getMessage());
  }
}
