package it.gov.pagopa.pu.processecexutions.repository.exportfile;

import it.gov.pagopa.pu.processecexutions.model.exportfile.PaymentsReportingExportFile;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "exportFiles", path = "payments-reporting-export-files")
public interface PaymentsReportingExportFileRepository extends JpaRepository<PaymentsReportingExportFile, Long> {

  @RestResource(exported = false)
  @Nonnull
  @Override
  <S extends PaymentsReportingExportFile> S save(@Nonnull S entity);
}
