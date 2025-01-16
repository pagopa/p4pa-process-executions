package it.gov.pagopa.pu.processecexutions.mapper;

import it.gov.pagopa.pu.processecexutions.dto.generated.IngestionFlowFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileStatus;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileType;
import it.gov.pagopa.pu.processecexutions.model.IngestionFlowFile;
import it.gov.pagopa.pu.processecexutions.util.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

class IngestionFlowFileRequestMapperTest {

  private final IngestionFlowFileRequestMapper mapper = new IngestionFlowFileRequestMapper();

  @Test
  void test() {
    // Given
    IngestionFlowFileRequestDTO dto = IngestionFlowFileRequestDTO.builder()
      .organizationId(0L)
      .filePathName("FILEPATHNAME")
      .fileName("FILENAME")
      .fileSize(1L)
      .flowFileType(IngestionFlowFileRequestDTO.FlowFileTypeEnum.PAYMENTS_REPORTING)
      .flowDateTime(OffsetDateTime.MIN)
      .pspIdentifier("PSPIDENTIFIER")
      .fileOrigin(IngestionFlowFileRequestDTO.FileOriginEnum.PORTAL)
      .build();

    // When
    IngestionFlowFile result = mapper.map(dto);

    // Then
    Assertions.assertNotNull(result);

    Assertions.assertEquals(0L, result.getOrganizationId());
    Assertions.assertEquals("FILEPATHNAME", result.getFilePathName());
    Assertions.assertEquals("FILENAME", result.getFileName());
    Assertions.assertEquals(1L, result.getFileSize());
    Assertions.assertEquals(IngestionFlowFileType.PAYMENTS_REPORTING, result.getFlowFileType());
    Assertions.assertEquals(IngestionFlowFileStatus.UPLOADED, result.getStatus());
    Assertions.assertSame(dto.getFlowDateTime(), result.getFlowDateTime());
    Assertions.assertEquals(dto.getPspIdentifier(), result.getPspIdentifier());
    Assertions.assertEquals(IngestionFlowFileRequestDTO.FileOriginEnum.PORTAL.name(), result.getFileOrigin());

    TestUtils.checkNotNullFields(result,
      "ingestionFlowFileId",
      "codError",
      "discardFileName",
      "numTotalRows",
      "numCorrectlyImportedRows",
      "pdfGenerated"
    );
  }
}
