package it.gov.pagopa.pu.processecexutions.repository;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileStatus;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileTypeEnum;
import it.gov.pagopa.pu.processecexutions.model.IngestionFlowFile;
import jakarta.annotation.Nonnull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "ingestion-flow-files")
public interface IngestionFlowFileRepository extends JpaRepository<IngestionFlowFile, Long> {

  @RestResource(exported = false)
  @Nonnull
  @Override
  <S extends IngestionFlowFile> S save(@Nonnull S entity);

  @RestResource(exported = false)
  @Modifying
  @Transactional
  @Query("update IngestionFlowFile" +
    " set status=:newStatus," +
    " fileVersion = COALESCE(:fileVersion, fileVersion)," +
    " numCorrectlyImportedRows=:processedRows," +
    " numTotalRows=:totalRows," +
    " errorDescription=:errorDescription," +
    " discardFileName=:discardFileName " +
    "where ingestionFlowFileId=:ingestionFlowFileId" +
    " and status=:oldStatus")
  int updateStatus(Long ingestionFlowFileId, String fileVersion, IngestionFlowFileStatus oldStatus, IngestionFlowFileStatus newStatus,
                   long processedRows, long totalRows,
                   String errorDescription, String discardFileName);

  @RestResource(exported = false)@Modifying
  @Transactional
  @Query("update IngestionFlowFile" +
    " set fileName=:fileName," +
    " discardFileName=:discardFileName " +
    "where ingestionFlowFileId=:ingestionFlowFileId")
  int updateFileNames(Long ingestionFlowFileId, String fileName, String discardFileName);

  @SuppressWarnings("squid:S107") // suppressing too many parameters warning: it's allowed in query methods
  @Query("SELECT iff "
    + "FROM IngestionFlowFile iff "
    + "WHERE iff.organizationId = :organizationId "
    + "AND iff.ingestionFlowFileType IN :ingestionFlowFileTypes "
    + "AND (cast(:creationDateFrom as date) IS NULL OR iff.creationDate >= :creationDateFrom) "
    + "AND (cast(:creationDateTo as date) IS NULL OR iff.creationDate <= :creationDateTo) "
    + "AND (:fileName IS NULL OR iff.fileName ILIKE CONCAT('%', cast(:fileName as text), '%')) "
    + "AND (:status IS NULL OR iff.status = :status) "
    + "AND (:operatorExternalId IS NULL OR iff.operatorExternalId = :operatorExternalId) ")
  Page<IngestionFlowFile> findByOrganizationIDFlowTypeCreateDate(@Parameter(required = true) @Param("organizationId") Long organizationId,
    @Parameter(required = true, array = @ArraySchema(schema = @Schema(type = "string"))) @Param("ingestionFlowFileTypes") List<IngestionFlowFileTypeEnum> ingestionFlowFileTypes,
    @Parameter(schema = @Schema(type = "LocalDateTime")) @Param("creationDateFrom") LocalDateTime creationDateFrom,
    @Parameter(schema = @Schema(type = "LocalDateTime")) @Param("creationDateTo") LocalDateTime creationDateTo,
    IngestionFlowFileStatus status,
    String fileName,
    String operatorExternalId,
    Pageable pageable);

  @Modifying
  @Transactional
  @Query("""
    UPDATE IngestionFlowFile iff
    SET iff.status = 'PROCESSING'
    WHERE iff.ingestionFlowFileId = :ingestionFlowFileId
    AND NOT EXISTS (
        SELECT 1 FROM IngestionFlowFile other
        WHERE other.organizationId = iff.organizationId
        AND other.ingestionFlowFileType = iff.ingestionFlowFileType
        AND other.status = 'PROCESSING'
        AND other.ingestionFlowFileId <> iff.ingestionFlowFileId
    )
    """)
  int updateProcessingIfNoOtherProcessing(@Param("ingestionFlowFileId") Long ingestionFlowFileId);

  Optional<IngestionFlowFile> findByOrganizationIdAndFilePathNameAndFileName(Long organizationId, String filePathName, String fileName);

  @RestResource(exported = false)
  @Modifying
  @Transactional
  @Query("UPDATE IngestionFlowFile" +
    " SET pdfGenerated=:pdfGenerated," +
    " pdfGeneratedId=:pdfGeneratedId " +
    "where ingestionFlowFileId=:ingestionFlowFileId")
  int updatePdfGeneratedAndPdfGeneratedId(@Param("ingestionFlowFileId") Long ingestionFlowFileId,
                                          @Param("pdfGenerated") long pdfGenerated,
                                          @Param("pdfGeneratedId") String pdfGeneratedId);
}
