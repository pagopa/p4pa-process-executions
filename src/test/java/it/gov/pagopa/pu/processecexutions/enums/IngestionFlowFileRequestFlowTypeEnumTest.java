package it.gov.pagopa.pu.processecexutions.enums;

import it.gov.pagopa.pu.processecexutions.dto.generated.IngestionFlowFileRequestDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IngestionFlowFileRequestFlowTypeEnumTest {

  @Test
  void testConversion(){
    for (IngestionFlowFileRequestDTO.FlowFileTypeEnum value : IngestionFlowFileRequestDTO.FlowFileTypeEnum.values()) {
      Assertions.assertDoesNotThrow(() -> IngestionFlowFileType.valueOf(value.name()));
    }

  }
}
