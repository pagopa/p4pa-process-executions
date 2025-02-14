package it.gov.pagopa.pu.processecexutions.service;

import it.gov.pagopa.pu.processecexutions.dto.generated.ExportFileRequestDTO;
import it.gov.pagopa.pu.processecexutions.dto.generated.ExportFileRequestDTO.FlowFileTypeEnum;
import it.gov.pagopa.pu.processecexutions.mapper.ExportFileRequestMapper;
import it.gov.pagopa.pu.processecexutions.model.ExportFile;
import it.gov.pagopa.pu.processecexutions.model.exportfile.ClassificationsExportFile;
import it.gov.pagopa.pu.processecexutions.model.exportfile.PaidExportFile;
import it.gov.pagopa.pu.processecexutions.model.exportfile.PaymentsReportingExportFile;
import it.gov.pagopa.pu.processecexutions.repository.exportfile.ExportFileRepository;
import org.springframework.stereotype.Service;

@Service
public class ExportFileServiceImpl implements ExportFileService{

  private final ExportFileRequestMapper uploadedRequestMapper;
  private final ExportFileRepository repository;
//TODO: exportFileWorkflowServices

  public ExportFileServiceImpl(ExportFileRequestMapper uploadedRequestMapper, ExportFileRepository repository) {
    this.uploadedRequestMapper = uploadedRequestMapper;
    this.repository = repository;
  }

  @Override
  public <T extends ExportFile<?>> T handleUploaded(ExportFileRequestDTO requestDTO,
    String operatorExternalId,
    String accessToken) {
    ExportFile<?> saved = repository.save(uploadedRequestMapper.map(requestDTO, operatorExternalId, createExportFileByType(requestDTO.getFlowFileType())));
//  TODO: call workflow services based on ExportFile type
    return (T) saved;
  }

  private ExportFile<?> createExportFileByType(FlowFileTypeEnum fileType) {
    return switch (fileType) {
      case CLASSIFICATIONS -> new ClassificationsExportFile();
      case PAID -> new PaidExportFile();
      case PAYMENTS_REPORTING -> new PaymentsReportingExportFile();
    };
  }
}
