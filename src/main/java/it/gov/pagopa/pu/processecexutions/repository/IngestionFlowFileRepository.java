package it.gov.pagopa.pu.processecexutions.repository;

import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileStatus;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileType;
import it.gov.pagopa.pu.processecexutions.model.IngestionFlowFile;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
}
