package it.gov.pagopa.pu.processecexutions.service;

import it.gov.pagopa.pu.processecexutions.connector.workflowhub.ExportFileService;
import it.gov.pagopa.pu.processecexutions.dto.ExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.enums.ExportFileType;
import it.gov.pagopa.pu.processecexutions.mapper.ExportFileRequestMapper;
import it.gov.pagopa.pu.processecexutions.model.ExportFile;
import it.gov.pagopa.pu.processecexutions.model.exportfile.ClassificationsExportFile;
import it.gov.pagopa.pu.processecexutions.model.exportfile.ExportFileFilter;
import it.gov.pagopa.pu.processecexutions.model.exportfile.PaidExportFile;
import it.gov.pagopa.pu.processecexutions.model.exportfile.PaymentsReportingExportFile;
import it.gov.pagopa.pu.processecexutions.repository.exportfile.ExportFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExportFileSaveServiceImpl implements ExportFileSaveService {

  private final ExportFileRequestMapper requestMapper;
  private final ExportFileRepository repository;
  private final ExportFileService exportFileService;


  public ExportFileSaveServiceImpl(ExportFileRequestMapper requestMapper,
                                   ExportFileRepository repository, ExportFileService exportFileService) {
    this.requestMapper = requestMapper;
    this.repository = repository;
    this.exportFileService = exportFileService;
  }

  @Transactional
  @Override
  public <R extends ExportFileFilter> ExportFile<R> save(
    ExportFileRequestDTO<R> requestDTO,
    String operatorExternalId,
    String accessToken) {

    ExportFile<R> saved = repository.save(
      requestMapper.map(requestDTO, operatorExternalId,
        (ExportFile<R>) createExportFileByType(requestDTO.getExportFileType())));

    exportFileService.invokeExportFileWorkflow(saved, accessToken);

    return saved;
  }

  private ExportFile<?> createExportFileByType(ExportFileType fileType) {
    return switch (fileType) {
      case CLASSIFICATIONS -> new ClassificationsExportFile();
      case PAID -> new PaidExportFile();
      case PAYMENTS_REPORTING -> new PaymentsReportingExportFile();
    };
  }
}
