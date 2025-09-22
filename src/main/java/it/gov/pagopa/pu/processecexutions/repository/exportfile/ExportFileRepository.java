package it.gov.pagopa.pu.processecexutions.repository.exportfile;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import it.gov.pagopa.pu.processecexutions.enums.ExportFileStatus;
import it.gov.pagopa.pu.processecexutions.enums.ExportFileTypeEnum;
import it.gov.pagopa.pu.processecexutions.model.ExportFile;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@RepositoryRestResource(path = "export-files")
public interface ExportFileRepository extends JpaRepository<ExportFile<?>, Long> {

  @RestResource(exported = false)
  @Nonnull
  @Override
  <S extends ExportFile<?>> S save(@Nonnull S entity);

  @SuppressWarnings("squid:S107") // suppressing too many parameters warning: it's allowed in query methods
  @Query("SELECT ef "
    + "FROM ExportFile ef "
    + "WHERE ef.organizationId = :organizationId "
    + "AND ef.exportFileType = :exportFileType "
    + "AND (cast(:creationDateFrom as date) IS NULL OR ef.creationDate >= :creationDateFrom) "
    + "AND (cast(:creationDateTo as date) IS NULL OR ef.creationDate <= :creationDateTo) "
    + "AND (:operatorExternalId IS NULL OR ef.operatorExternalId = :operatorExternalId) "
    + "AND (:fileName IS NULL OR ef.fileName ILIKE CONCAT('%', cast(:fileName as text), '%')) "
    + "AND (:status IS NULL OR ef.status = :status) ")
  Page<ExportFile<?>> findByOrganizationIDFlowTypeCreateDate(
    @Parameter(required = true) @Param("organizationId") Long organizationId,
    @Parameter(required = true) @Param("exportFileType") ExportFileTypeEnum exportFileType,
    @Parameter(schema = @Schema(type = "LocalDateTime")) @Param("creationDateFrom") LocalDateTime creationDateFrom,
    @Parameter(schema = @Schema(type = "LocalDateTime")) @Param("creationDateTo")LocalDateTime creationDateTo,
    String operatorExternalId,
    ExportFileStatus status,
    String fileName,
    Pageable pageable
  );

  @SuppressWarnings("squid:S107") // suppressing too many parameters warning: it's allowed in query methods
  @Modifying
  @Transactional
  @Query("UPDATE ExportFile " +
    "SET status=:newStatus, " +
    "filePathName=:filePathName, " +
    "fileName=:fileName, " +
    "fileSize=:fileSize, " +
    "numTotalRows=:numTotalRows, " +
    "errorDescription=:errorDescription, " +
    "expirationDate=:expirationDate " +
    "WHERE exportFileId=:exportFileId " +
    "AND status=:oldStatus")
  Integer updateStatus(Long exportFileId, ExportFileStatus oldStatus, ExportFileStatus newStatus, String filePathName, String fileName, Long fileSize, Long numTotalRows, String errorDescription, OffsetDateTime expirationDate);

}
