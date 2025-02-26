package it.gov.pagopa.pu.processecexutions.mapper;

import it.gov.pagopa.pu.processecexutions.dto.ExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.enums.ExportFileStatus;
import it.gov.pagopa.pu.processecexutions.model.ExportFile;
import it.gov.pagopa.pu.processecexutions.model.exportfile.ExportFileFilter;
import org.springframework.stereotype.Service;

@Service
public class ExportFileRequestMapper {

  public <T extends ExportFile<R>, R extends ExportFileFilter> T map(ExportFileRequestDTO<R> dto,
    String operatorExternalId, T exportFile) {
    exportFile.setOrganizationId(dto.getOrganizationId());
    exportFile.setOperatorExternalId(operatorExternalId);
    exportFile.setStatus(ExportFileStatus.REQUESTED);
    exportFile.setFilterFields(dto.getFilterFields());
    return exportFile;
  }

}
