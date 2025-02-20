package it.gov.pagopa.pu.processecexutions.service;

import it.gov.pagopa.pu.processecexutions.dto.ExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.enums.ExportFlowFileType;
import it.gov.pagopa.pu.processecexutions.mapper.ExportFileRequestMapper;
import it.gov.pagopa.pu.processecexutions.model.ExportFile;
import it.gov.pagopa.pu.processecexutions.model.exportfile.ClassificationsExportFile;
import it.gov.pagopa.pu.processecexutions.model.exportfile.PaidExportFile;
import it.gov.pagopa.pu.processecexutions.model.exportfile.PaymentsReportingExportFile;
import it.gov.pagopa.pu.processecexutions.repository.exportfile.ExportFileRepository;
import org.springframework.stereotype.Service;

@Service
public class ExportFileServiceImpl implements ExportFileService{

  private final ExportFileRequestMapper requestMapper;
  private final ExportFileRepository repository;

  public ExportFileServiceImpl(ExportFileRequestMapper requestMapper, ExportFileRepository repository) {
    this.requestMapper = requestMapper;
    this.repository = repository;
  }

  @Override
  @SuppressWarnings("rawtypes")
  public ExportFile<?> save(ExportFileRequestDTO requestDTO,
    String operatorExternalId,
    String accessToken) {

    ExportFile<?> saved = repository.save(
      requestMapper.map(requestDTO, operatorExternalId,
        createExportFileByType(requestDTO.getFlowFileType())));

    /*  TODO: call workflow services based on ExportFile type
    [P4ADEV-2225] CLASSIFICATIONS
    [P4ADEV-2223] PAID
    [P4ADEV-2224] PAYMENTS_REPORTING
    */

    return saved;
  }

  private ExportFile<?> createExportFileByType(ExportFlowFileType fileType) {
    return switch (fileType) {
      case CLASSIFICATIONS -> new ClassificationsExportFile();
      case PAID -> new PaidExportFile();
      case PAYMENTS_REPORTING -> new PaymentsReportingExportFile();
    };
  }
}
