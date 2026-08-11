package it.gov.pagopa.pu.processexecutions.service;

import it.gov.pagopa.pu.processexecutions.connector.workflowhub.ExportFileService;
import it.gov.pagopa.pu.processexecutions.dto.exportFile.ExportFileRequestDTO;
import it.gov.pagopa.pu.processexecutions.enums.ExportFileTypeEnum;
import it.gov.pagopa.pu.processexecutions.mapper.ExportFileRequestMapper;
import it.gov.pagopa.pu.processexecutions.model.ExportFile;
import it.gov.pagopa.pu.processexecutions.model.exportfile.*;
import it.gov.pagopa.pu.processexecutions.repository.exportfile.ExportFileRepository;
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

  private ExportFile<?> createExportFileByType(ExportFileTypeEnum fileType) {
    return switch (fileType) {
      case CLASSIFICATIONS -> new ClassificationsExportFile();
      case PAID -> new PaidExportFile();
      case PAYMENTS_REPORTING -> new PaymentsReportingExportFile();
      case RECEIPTS_ARCHIVING -> new ReceiptsArchivingExportFile();
    };
  }
}
