package it.gov.pagopa.pu.processecexutions.mapper;

import it.gov.pagopa.pu.processecexutions.dto.exportFile.ExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.enums.ExportFileStatus;
import it.gov.pagopa.pu.processecexutions.enums.ExportFileTypeEnum;
import it.gov.pagopa.pu.processecexutions.exception.ExportFlowFileVersionNotSupportedException;
import it.gov.pagopa.pu.processecexutions.model.ExportFile;
import it.gov.pagopa.pu.processecexutions.model.exportfile.ExportFileFilter;
import it.gov.pagopa.pu.processecexutions.model.exportfile.ExportFileTypeVersions;
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

  private @NotNull String transcodeFileVersion(@NotNull ExportFileTypeEnum exportFileType, @NotNull String fileVersion) {
    List<ExportFileTypeVersions> availableVersions = ExportConstants.getAvailableVersions(exportFileType);
    return availableVersions.stream()
      .filter(v -> v.toString().equals(fileVersion))
      .findFirst()
      .map(x -> fileVersion)
      .orElseThrow(() ->
        new ExportFlowFileVersionNotSupportedException("File version " + fileVersion + " not supported for " + exportFileType + ": Available versions are: " + availableVersions)
      );
  }

}
