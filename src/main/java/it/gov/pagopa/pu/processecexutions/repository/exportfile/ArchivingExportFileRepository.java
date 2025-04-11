package it.gov.pagopa.pu.processecexutions.repository.exportfile;

import it.gov.pagopa.pu.processecexutions.model.exportfile.ArchivingExportFile;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "exportFiles", path = "archiving-export-files")
public interface ArchivingExportFileRepository extends JpaRepository<ArchivingExportFile, Long> {

  @RestResource(exported = false)
  @Nonnull
  @Override
  <S extends ArchivingExportFile> S save(@Nonnull S entity);
}
