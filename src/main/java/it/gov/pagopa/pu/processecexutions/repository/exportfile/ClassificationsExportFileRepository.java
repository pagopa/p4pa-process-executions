package it.gov.pagopa.pu.processecexutions.repository.exportfile;

import it.gov.pagopa.pu.processecexutions.model.exportfile.ClassificationsExportFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "exportFiles", path = "classifications-export-files")
public interface ClassificationsExportFileRepository extends JpaRepository<ClassificationsExportFile, Long> {
}
