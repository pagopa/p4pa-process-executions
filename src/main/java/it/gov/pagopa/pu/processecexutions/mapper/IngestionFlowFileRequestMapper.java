package it.gov.pagopa.pu.processecexutions.mapper;

import it.gov.pagopa.pu.processecexutions.dto.generated.IngestionFlowFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileStatus;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileType;
import it.gov.pagopa.pu.processecexutions.model.IngestionFlowFile;
import org.springframework.stereotype.Service;

@Service
public class IngestionFlowFileRequestMapper {

  public IngestionFlowFile map(IngestionFlowFileRequestDTO dto) {
    return IngestionFlowFile.builder()
      .organizationId(dto.getOrganizationId())
      .filePathName(dto.getFilePathName())
      .fileName(dto.getFileName())
      .fileSize(dto.getFileSize())
      .flowFileType(IngestionFlowFileType.valueOf(dto.getFlowFileType().name()))
      .status(IngestionFlowFileStatus.UPLOADED)
      .flowDateTime(dto.getFlowDateTime())
      .pspIdentifier(dto.getPspIdentifier())
      .fileOrigin(dto.getFileOrigin())
      .build();
  }
}
