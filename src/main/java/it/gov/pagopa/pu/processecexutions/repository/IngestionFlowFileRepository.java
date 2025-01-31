package it.gov.pagopa.pu.processecexutions.repository;

import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileStatus;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileType;
import it.gov.pagopa.pu.processecexutions.model.IngestionFlowFile;
import jakarta.annotation.Nonnull;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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
  @Query("update IngestionFlowFile set status=:status, codError=:codError, discardFileName=:discardFileName where ingestionFlowFileId=:ingestionFlowFileId")
  int updateStatus(Long ingestionFlowFileId, IngestionFlowFileStatus status, String codError, String discardFileName);

  @Query("SELECT iff "
    + "FROM IngestionFlowFile iff "
    + "WHERE iff.organizationId = :organizationId "
    + "AND iff.flowFileType = :flowFileType "
    + "AND (:creationDate IS NULL OR iff.creationDate > :creationDate) "
    + "AND (:filename IS NULL OR iff.fileName = :fileName) ")
  Page<IngestionFlowFile> findByOrganizationIDFlowTypeCreateDate(Long organizationId, IngestionFlowFileType flowFileType, LocalDateTime creationDate, String fileName, Pageable pageable);

  @Query("SELECT iff "
    + "FROM IngestionFlowFile iff "
    + "WHERE (:organizationId IS NULL OR iff.organizationId = :organizationId) "
    + "AND (:flowFileType IS NULL OR iff.flowFileType = :flowFileType) "
    + "AND (:fileName IS NULL OR iff.fileName ILIKE CONCAT('%', cast(:fileName as text), '%')) "
    + "AND ((cast(:fromCreationDate as date) IS NULL AND cast(:toCreationDate as date) IS NULL) "
      + "OR (cast(:fromCreationDate as date) IS NULL AND cast(:toCreationDate as date) IS NOT NULL AND iff.creationDate <= :toCreationDate) "
      + "OR (cast(:toCreationDate as date) IS NULL AND cast(:fromCreationDate as date) IS NOT NULL AND iff.creationDate >= :fromCreationDate) "
      + "OR (iff.creationDate BETWEEN :fromCreationDate AND :toCreationDate)) "
    + "AND (:status IS NULL OR iff.status = :status) ")
  Page<IngestionFlowFile> findIngestionFlowFiles(Long organizationId,IngestionFlowFileType flowFileType,String fileName,LocalDateTime fromCreationDate,LocalDateTime toCreationDate,IngestionFlowFileStatus status, Pageable pageable);
}
