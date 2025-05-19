package it.gov.pagopa.pu.processecexutions.mapper;

import it.gov.pagopa.pu.processecexutions.dto.generated.IngestionFlowFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.enums.DpInstallmentsIngestionFlowFileVersion;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileStatus;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileTypeEnum;
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
      .ingestionFlowFileType(IngestionFlowFileTypeEnum.PAYMENTS_REPORTING)
      .flowDateTime(OffsetDateTime.MIN)
      .pspIdentifier("PSPIDENTIFIER")
      .fileOrigin("portal")
      .ingestionFlowFileVersion(DpInstallmentsIngestionFlowFileVersion.V1_0.getValue())
      .build();

    // When
    IngestionFlowFile result = mapper.map(dto, "OPERATOREXTERNALID");

    // Then
    Assertions.assertNotNull(result);

    Assertions.assertEquals(0L, result.getOrganizationId());
    Assertions.assertEquals("OPERATOREXTERNALID", result.getOperatorExternalId());
    Assertions.assertEquals("FILEPATHNAME", result.getFilePathName());
    Assertions.assertEquals("FILENAME", result.getFileName());
    Assertions.assertEquals(1L, result.getFileSize());
    Assertions.assertEquals(IngestionFlowFileTypeEnum.PAYMENTS_REPORTING, result.getIngestionFlowFileType());
    Assertions.assertEquals(IngestionFlowFileStatus.UPLOADED, result.getStatus());
    Assertions.assertSame(dto.getFlowDateTime(), result.getFlowDateTime());
    Assertions.assertEquals(dto.getPspIdentifier(), result.getPspIdentifier());
    Assertions.assertEquals("portal", result.getFileOrigin());
    Assertions.assertEquals(DpInstallmentsIngestionFlowFileVersion.V1_0.getValue(), result.getFileVersion());

    TestUtils.checkNotNullFields(result,
      "ingestionFlowFileId",
      "errorDescription",
      "discardFileName",
      "numTotalRows",
      "numCorrectlyImportedRows",
      "pdfGenerated",
      "pdfGeneratedId",
      "creationDate",
      "updateDate",
      "updateOperatorExternalId",
      "updateTraceId"
    );
  }
}
