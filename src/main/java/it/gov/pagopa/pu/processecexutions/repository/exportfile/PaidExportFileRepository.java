package it.gov.pagopa.pu.processecexutions.repository.exportfile;

import it.gov.pagopa.pu.processecexutions.model.exportfile.PaidExportFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "exportFiles", path = "paid-export-files")
public interface PaidExportFileRepository extends JpaRepository<PaidExportFile, Long> {
}
