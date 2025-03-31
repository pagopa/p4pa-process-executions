package it.gov.pagopa.pu.processecexutions.mapper;

import it.gov.pagopa.pu.processecexutions.dto.ExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.enums.ExportFileStatus;
import it.gov.pagopa.pu.processecexutions.enums.ExportFileType;
import it.gov.pagopa.pu.processecexutions.exception.ExportFlowFileVersionNotSupportedException;
import it.gov.pagopa.pu.processecexutions.model.ExportFile;
import it.gov.pagopa.pu.processecexutions.model.exportfile.ExportFileFilter;
import it.gov.pagopa.pu.processecexutions.util.ExportConstants;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExportFileRequestMapper {

  public <T extends ExportFile<R>, R extends ExportFileFilter> T map(ExportFileRequestDTO<R> dto,
    String operatorExternalId, T exportFile) {
    exportFile.setOrganizationId(dto.getOrganizationId());
    exportFile.setOperatorExternalId(operatorExternalId);
    exportFile.setFileVersion(transcodeFileVersion(dto.getExportFileType(), dto.getFileVersion()));
    exportFile.setStatus(ExportFileStatus.REQUESTED);
    exportFile.setFilterFields(dto.getFilterFields());
    return exportFile;
  }

  private @NotNull String transcodeFileVersion(@NotNull ExportFileType exportFileType, @NotNull String fileVersion) {
    List<String> availableVersions = ExportConstants.getAvailableVersions(exportFileType);
    if(availableVersions.contains(fileVersion)){
      return fileVersion;
    } else {
      throw new ExportFlowFileVersionNotSupportedException("File version " + fileVersion + " not supported for " + exportFileType + ": Available versions are: " + availableVersions);
    }
  }

}
