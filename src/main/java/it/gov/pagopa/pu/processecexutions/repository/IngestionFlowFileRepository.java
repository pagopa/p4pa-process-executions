package it.gov.pagopa.pu.processecexutions.repository;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileStatus;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileType;
import it.gov.pagopa.pu.processecexutions.model.IngestionFlowFile;
import jakarta.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource(path = "ingestion-flow-files")
public interface IngestionFlowFileRepository extends JpaRepository<IngestionFlowFile, Long> {

  @RestResource(exported = false)
  @Nonnull
  @Override
  <S extends IngestionFlowFile> S save(@Nonnull S entity);

  @Modifying
  @Transactional
  @Query("update IngestionFlowFile" +
    " set status=:newStatus," +
    " errorDescription=:errorDescription," +
    " discardFileName=:discardFileName " +
    "where ingestionFlowFileId=:ingestionFlowFileId" +
    " and status=:oldStatus")
  int updateStatus(Long ingestionFlowFileId, IngestionFlowFileStatus oldStatus, IngestionFlowFileStatus newStatus, String errorDescription, String discardFileName);

  @SuppressWarnings("squid:S107") // suppressing too many parameters warning: it's allowed in query methods
  @Query("SELECT iff "
    + "FROM IngestionFlowFile iff "
    + "WHERE iff.organizationId = :organizationId "
    + "AND iff.flowFileType IN :flowFileTypes "
    + "AND (cast(:creationDateFrom as date) IS NULL OR iff.creationDate >= :creationDateFrom) "
    + "AND (cast(:creationDateTo as date) IS NULL OR iff.creationDate <= :creationDateTo) "
    + "AND (:fileName IS NULL OR iff.fileName ILIKE CONCAT('%', cast(:fileName as text), '%')) "
    + "AND (:status IS NULL OR iff.status = :status) "
    + "AND (:operatorExternalId IS NULL OR iff.operatorExternalId = :operatorExternalId) ")
  Page<IngestionFlowFile> findByOrganizationIDFlowTypeCreateDate(@Parameter(required = true) @Param("organizationId") Long organizationId,
    @Parameter(required = true, array = @ArraySchema(schema = @Schema(type = "string"))) @Param("flowFileTypes") List<IngestionFlowFileType> flowFileTypes,
    @Parameter(schema = @Schema(type = "LocalDateTime")) @Param("creationDateFrom") LocalDateTime creationDateFrom,
    @Parameter(schema = @Schema(type = "LocalDateTime")) @Param("creationDateTo") LocalDateTime creationDateTo,
    IngestionFlowFileStatus status,
    String fileName,
    String operatorExternalId,
    Pageable pageable);
}
