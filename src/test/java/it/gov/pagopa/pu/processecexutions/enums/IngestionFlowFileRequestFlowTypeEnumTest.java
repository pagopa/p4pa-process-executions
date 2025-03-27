package it.gov.pagopa.pu.processecexutions.enums;

import it.gov.pagopa.pu.processecexutions.dto.generated.IngestionFlowFileRequestDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IngestionFlowFileRequestFlowTypeEnumTest {

  @Test
  void testConversion(){
    for (IngestionFlowFileRequestDTO.IngestionFlowFileTypeEnum value : IngestionFlowFileRequestDTO.IngestionFlowFileTypeEnum.values()) {
      Assertions.assertDoesNotThrow(() -> IngestionFlowFileType.valueOf(value.getValue()));
    }

  }
}
