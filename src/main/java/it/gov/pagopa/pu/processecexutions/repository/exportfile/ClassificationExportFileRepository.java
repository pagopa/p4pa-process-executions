package it.gov.pagopa.pu.processecexutions.repository.exportfile;

import it.gov.pagopa.pu.processecexutions.model.exportfile.ClassificationExportFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "classification-export-files")
public interface ClassificationExportFileRepository extends JpaRepository<ClassificationExportFile, Long> {
}
