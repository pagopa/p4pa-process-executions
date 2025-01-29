package it.gov.pagopa.pu.processecexutions.repository.exportfile;

import it.gov.pagopa.pu.processecexutions.model.ExportFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "export-files")
public interface ExportFileRepository extends JpaRepository<ExportFile<?>, Long> {
}
