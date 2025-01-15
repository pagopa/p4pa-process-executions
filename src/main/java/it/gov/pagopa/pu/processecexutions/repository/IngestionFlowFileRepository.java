package it.gov.pagopa.pu.processecexutions.repository;

import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileStatus;
import it.gov.pagopa.pu.processecexutions.model.IngestionFlowFile;
import jakarta.annotation.Nonnull;
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
}
