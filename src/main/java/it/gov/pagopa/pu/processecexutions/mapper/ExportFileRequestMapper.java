package it.gov.pagopa.pu.processecexutions.mapper;

import it.gov.pagopa.pu.processecexutions.dto.generated.ExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.enums.ExportFileStatus;
import it.gov.pagopa.pu.processecexutions.model.ExportFile;
import org.springframework.stereotype.Service;

@Service
public class ExportFileRequestMapper {

  public <T extends ExportFile<?>> T map(ExportFileRequestDTO dto, String operatorExternalId, T exportFile) {
      exportFile.setOrganizationId(dto.getOrganizationId());
      exportFile.setOperatorExternalId(operatorExternalId);
      exportFile.setFilePathName(dto.getFilePathName());
      exportFile.setFileName(dto.getFileName());
      exportFile.setFileSize(dto.getFileSize());
      exportFile.setStatus(ExportFileStatus.REQUESTED); // TODO: correct status?
      return exportFile;
  }

}
