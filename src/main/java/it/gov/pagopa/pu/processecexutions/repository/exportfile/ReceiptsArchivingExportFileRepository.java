package it.gov.pagopa.pu.processecexutions.repository.exportfile;

import it.gov.pagopa.pu.processecexutions.model.exportfile.ReceiptsArchivingExportFile;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "exportFiles", path = "receipts-archiving-export-files")
public interface ReceiptsArchivingExportFileRepository extends JpaRepository<ReceiptsArchivingExportFile, Long> {

  @RestResource(exported = false)
  @Nonnull
  @Override
  <S extends ReceiptsArchivingExportFile> S save(@Nonnull S entity);
}
