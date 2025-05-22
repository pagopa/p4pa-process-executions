package it.gov.pagopa.pu.processecexutions.mapper;

import it.gov.pagopa.pu.processecexutions.dto.generated.IngestionFlowFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileStatus;
import it.gov.pagopa.pu.processecexutions.model.IngestionFlowFile;
import org.springframework.stereotype.Service;

@Service
public class IngestionFlowFileRequestMapper {

  public IngestionFlowFile map(IngestionFlowFileRequestDTO dto, String operatorExternalId, IngestionFlowFileStatus status) {
    return IngestionFlowFile.builder()
      .organizationId(dto.getOrganizationId())
      .operatorExternalId(operatorExternalId)
      .filePathName(dto.getFilePathName())
      .fileName(dto.getFileName())
      .fileSize(dto.getFileSize())
      .ingestionFlowFileType(dto.getIngestionFlowFileType())
      .status(status)
      .flowDateTime(dto.getFlowDateTime())
      .pspIdentifier(dto.getPspIdentifier())
      .fileOrigin(dto.getFileOrigin())
      .fileVersion(dto.getFileVersion())
      .build();
  }

  public IngestionFlowFile update(IngestionFlowFile entity, IngestionFlowFileRequestDTO dto, String operatorExternalId, IngestionFlowFileStatus status) {
    return entity.toBuilder()
      .organizationId(dto.getOrganizationId())
      .operatorExternalId(operatorExternalId)
      .filePathName(dto.getFilePathName())
      .fileName(dto.getFileName())
      .fileSize(dto.getFileSize())
      .ingestionFlowFileType(dto.getIngestionFlowFileType())
      .status(status)
      .flowDateTime(dto.getFlowDateTime())
      .pspIdentifier(dto.getPspIdentifier())
      .fileOrigin(dto.getFileOrigin())
      .fileVersion(dto.getFileVersion())
      .build();
  }
}
