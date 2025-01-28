package it.gov.pagopa.pu.processecexutions.repository.exportfile;

import it.gov.pagopa.pu.processecexutions.model.exportfile.PaymentsReportingExportFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "exportFiles", path = "payments-reporting-export-files")
public interface PaymentsReportingExportFileRepository extends JpaRepository<PaymentsReportingExportFile, Long> {
}
