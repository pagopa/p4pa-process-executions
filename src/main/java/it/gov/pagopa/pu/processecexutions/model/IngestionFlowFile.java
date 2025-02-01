package it.gov.pagopa.pu.processecexutions.model;

import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileStatus;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Entity
@Table(name = "ingestion_flow_file")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(of = "ingestionFlowFileId", callSuper = false)
public class IngestionFlowFile extends BaseEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingestion_flow_file_generator")
  @SequenceGenerator(name = "ingestion_flow_file_generator", sequenceName = "ingestion_flow_file_id_seq", allocationSize = 1)
  @NotNull
  private Long ingestionFlowFileId;
  @NotNull
  private Long organizationId;
  @NotNull
  private String operatorExternalId;
  @NotNull
  private String filePathName;
  @NotNull
  private String fileName;
  @NotNull
  private Long fileSize;
  @Enumerated(EnumType.STRING)
  @NotNull
  private IngestionFlowFileType flowFileType;
  @Enumerated(EnumType.STRING)
  @NotNull
  private IngestionFlowFileStatus status;
  private String codError;
  private String discardFileName;
  private Long numTotalRows;
  private Long numCorrectlyImportedRows;
  private Long pdfGenerated;
  private String pspIdentifier;
  private OffsetDateTime flowDateTime;
  @NotNull
  private String fileOrigin;

}
