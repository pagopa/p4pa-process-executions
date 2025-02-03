package it.gov.pagopa.pu.processecexutions.repository.exportfile;

import io.swagger.v3.oas.annotations.Parameter;
import it.gov.pagopa.pu.processecexutions.enums.ExportFileStatus;
import it.gov.pagopa.pu.processecexutions.enums.ExportFlowFileType;
import it.gov.pagopa.pu.processecexutions.model.ExportFile;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "export-files")
public interface ExportFileRepository extends
  JpaRepository<ExportFile<?>, Long> {

  @Query("SELECT ef "
    + "FROM ExportFile ef "
    + "WHERE ef.organizationId = :organizationId "
    + "AND ef.flowFileType = :flowFileType "
    + "AND (cast(:creationDateFrom as date) IS NULL OR ef.creationDate >= :creationDateFrom) "
    + "AND (cast(:creationDateTo as date) IS NULL OR ef.creationDate <= :creationDateTo) "
    + "AND (:operatorExternalId IS NULL OR ef.operatorExternalId = :operatorExternalId) "
    + "AND (:fileName IS NULL OR ef.fileName ILIKE CONCAT('%', cast(:fileName as text), '%')) "
    + "AND (:status IS NULL OR ef.status = :status) ")
  Page<ExportFile<?>> findByOrganizationIDFlowTypeCreateDate(@Parameter(required = true) @Param("organizationId") Long organizationId, @Parameter(required = true) @Param("flowFileType") ExportFlowFileType flowFileType, LocalDateTime creationDateFrom, LocalDateTime creationDateTo, String operatorExternalId, ExportFileStatus status, String fileName, Pageable pageable);
}
