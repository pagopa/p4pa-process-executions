package it.gov.pagopa.pu.processecexutions.mapper;

import it.gov.pagopa.pu.processecexutions.dto.generated.IngestionFlowFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileStatus;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileTypeEnum;
import it.gov.pagopa.pu.processecexutions.enums.ingestion.version.DpInstallmentsIngestionFlowFileVersion;
import it.gov.pagopa.pu.processecexutions.model.IngestionFlowFile;
import it.gov.pagopa.pu.processecexutions.util.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.OffsetDateTime;
import java.util.stream.Stream;

class IngestionFlowFileRequestMapperTest {

  private final IngestionFlowFileRequestMapper mapper = new IngestionFlowFileRequestMapper();

  @ParameterizedTest
  @MethodSource("provideEnums")
  void testMap(IngestionFlowFileStatus status) {
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
      .fileVersion(DpInstallmentsIngestionFlowFileVersion.V1_0.getValue())
      .build();

    // When
    IngestionFlowFile result = mapper.map(dto, "OPERATOREXTERNALID", status);

    // Then
    Assertions.assertNotNull(result);

    Assertions.assertEquals(0L, result.getOrganizationId());
    Assertions.assertEquals("OPERATOREXTERNALID", result.getOperatorExternalId());
    Assertions.assertEquals("FILEPATHNAME", result.getFilePathName());
    Assertions.assertEquals("FILENAME", result.getFileName());
    Assertions.assertEquals(1L, result.getFileSize());
    Assertions.assertEquals(IngestionFlowFileTypeEnum.PAYMENTS_REPORTING, result.getIngestionFlowFileType());
    Assertions.assertEquals(status, result.getStatus());
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

  public static Stream<Arguments> provideEnums() {
    return Stream.of(
      Arguments.of(IngestionFlowFileStatus.UPLOADED),
      Arguments.of(IngestionFlowFileStatus.WAITING_FILE)
    );
  }

  @Test
  void testUpdate() {
    // Given
    IngestionFlowFileRequestDTO dto = IngestionFlowFileRequestDTO.builder()
      .organizationId(1L)
      .filePathName("UPDATED_FILEPATHNAME")
      .fileName("UPDATED_FILENAME")
      .fileSize(2L)
      .ingestionFlowFileType(IngestionFlowFileTypeEnum.PAYMENTS_REPORTING)
      .flowDateTime(OffsetDateTime.MAX)
      .pspIdentifier("UPDATED_PSPIDENTIFIER")
      .fileOrigin("api")
      .fileVersion(DpInstallmentsIngestionFlowFileVersion.V2_0.getValue())
      .build();

    IngestionFlowFile existingEntity = IngestionFlowFile.builder()
      .ingestionFlowFileId(1L)
      .organizationId(0L)
      .filePathName("FILEPATHNAME")
      .fileName("FILENAME")
      .fileSize(1L)
      .ingestionFlowFileType(IngestionFlowFileTypeEnum.PAYMENTS_REPORTING)
      .status(IngestionFlowFileStatus.WAITING_FILE)
      .flowDateTime(OffsetDateTime.MIN)
      .pspIdentifier("PSPIDENTIFIER")
      .fileOrigin("portal")
      .fileVersion(DpInstallmentsIngestionFlowFileVersion.V1_0.getValue())
      .build();

    // When
    IngestionFlowFile result = mapper.update(existingEntity, dto, "UPDATED_OPERATOREXTERNALID", IngestionFlowFileStatus.UPLOADED);

    // Then
    Assertions.assertEquals(existingEntity.getIngestionFlowFileId(), result.getIngestionFlowFileId());
    Assertions.assertEquals(1L, result.getOrganizationId());
    Assertions.assertEquals("UPDATED_OPERATOREXTERNALID", result.getOperatorExternalId());
    Assertions.assertEquals("UPDATED_FILEPATHNAME", result.getFilePathName());
    Assertions.assertEquals("UPDATED_FILENAME", result.getFileName());
    Assertions.assertEquals(2L, result.getFileSize());
    Assertions.assertEquals(IngestionFlowFileTypeEnum.PAYMENTS_REPORTING, result.getIngestionFlowFileType());
    Assertions.assertEquals(IngestionFlowFileStatus.UPLOADED, result.getStatus());
    Assertions.assertSame(dto.getFlowDateTime(), result.getFlowDateTime());
    Assertions.assertEquals(dto.getPspIdentifier(), result.getPspIdentifier());
    Assertions.assertEquals("api", result.getFileOrigin());
    Assertions.assertEquals(DpInstallmentsIngestionFlowFileVersion.V2_0.getValue(), result.getFileVersion());

    TestUtils.checkNotNullFields(result,
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
