package it.gov.pagopa.pu.processecexutions.repository.exportfile;

import it.gov.pagopa.pu.processecexutions.model.exportfile.ClassificationsExportFile;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "exportFiles", path = "classifications-export-files")
public interface ClassificationsExportFileRepository extends JpaRepository<ClassificationsExportFile, Long> {

  @RestResource(exported = false)
  @Nonnull
  @Override
  <S extends ClassificationsExportFile> S save(@Nonnull S entity);
}
