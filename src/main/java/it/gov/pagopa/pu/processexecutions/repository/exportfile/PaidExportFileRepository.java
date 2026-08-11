package it.gov.pagopa.pu.processexecutions.repository.exportfile;

import it.gov.pagopa.pu.processexecutions.model.exportfile.PaidExportFile;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "exportFiles", path = "paid-export-files")
public interface PaidExportFileRepository extends JpaRepository<PaidExportFile, Long> {

  @RestResource(exported = false)
  @Nonnull
  @Override
  <S extends PaidExportFile> S save(@Nonnull S entity);
}
