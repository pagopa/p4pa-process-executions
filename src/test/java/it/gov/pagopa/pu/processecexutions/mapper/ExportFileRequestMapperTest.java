package it.gov.pagopa.pu.processecexutions.mapper;

import it.gov.pagopa.pu.processecexutions.dto.ExportFileRequestDTO;
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
      .flowFileType(ExportFlowFileType.PAYMENTS_REPORTING)
      .build();

    // When
    ExportFile<?> result = mapper.map(dto, "OPERATOREXTERNALID", new PaymentsReportingExportFile());

    // Then
    Assertions.assertNotNull(result);

    Assertions.assertEquals(0L, result.getOrganizationId());
    Assertions.assertEquals("OPERATOREXTERNALID", result.getOperatorExternalId());
    Assertions.assertEquals(ExportFlowFileType.PAYMENTS_REPORTING, result.getFlowFileType());
    Assertions.assertEquals(ExportFileStatus.REQUESTED, result.getStatus());

    TestUtils.checkNotNullFields(result,
      "exportFileId",
      "filePathName",
      "fileSize",
      "fileName",
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
