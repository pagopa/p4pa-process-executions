package it.gov.pagopa.pu.processecexutions.mapper;

import it.gov.pagopa.pu.processecexutions.dto.ExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.enums.ExportFileStatus;
import it.gov.pagopa.pu.processecexutions.enums.ExportFlowFileType;
import it.gov.pagopa.pu.processecexutions.exception.ExportFlowFileVersionNotSupportedException;
import it.gov.pagopa.pu.processecexutions.model.exportfile.PaidExportFile;
import it.gov.pagopa.pu.processecexutions.model.exportfile.PaidExportFileFilter;
import it.gov.pagopa.pu.processecexutions.util.ExportConstants;
import it.gov.pagopa.pu.processecexutions.util.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExportFileRequestMapperTest {

  private final ExportFileRequestMapper mapper = new ExportFileRequestMapper();

  @Test
  void whenMapThenOk() {
    // Given
    ExportFileRequestDTO<PaidExportFileFilter> dto = ExportFileRequestDTO.<PaidExportFileFilter>builder()
      .organizationId(0L)
      .flowFileType(ExportFlowFileType.PAID)
      .flowFileVersion("v1.0")
      .filterFields(new PaidExportFileFilter())
      .build();

    // When
    PaidExportFile result = mapper.map(dto, "OPERATOREXTERNALID", new PaidExportFile());

    // Then
    Assertions.assertNotNull(result);

    Assertions.assertEquals(0L, result.getOrganizationId());
    Assertions.assertEquals("OPERATOREXTERNALID", result.getOperatorExternalId());
    Assertions.assertEquals(ExportFlowFileType.PAID, result.getFlowFileType());
    Assertions.assertEquals("v1.0", result.getFlowFileVersion());
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
      "updateOperatorExternalId"
    );
  }

  @Test
  void givenUnsupportedVersionWhenMapThen (){
    // Given
    ExportFileRequestDTO<PaidExportFileFilter> dto = ExportFileRequestDTO.<PaidExportFileFilter>builder()
      .organizationId(0L)
      .flowFileType(ExportFlowFileType.PAID)
      .flowFileVersion("NOTVALID")
      .filterFields(new PaidExportFileFilter())
      .build();
    PaidExportFile exportFile = new PaidExportFile();

    // When
    ExportFlowFileVersionNotSupportedException result = Assertions.assertThrows(ExportFlowFileVersionNotSupportedException.class, () -> mapper.map(dto, "OPERATOREXTERNALID", exportFile));

    // Then
    Assertions.assertEquals("Flow file version NOTVALID not supported for PAID: Available versions are: " + ExportConstants.getAvailableVersions(ExportFlowFileType.PAID),
      result.getMessage());
  }
}
