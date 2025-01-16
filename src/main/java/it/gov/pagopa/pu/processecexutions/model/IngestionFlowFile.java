package it.gov.pagopa.pu.processecexutions.model;

import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileStatus;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileType;
import jakarta.persistence.*;
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
public class IngestionFlowFile implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingestion_flow_file_generator")
  @SequenceGenerator(name = "ingestion_flow_file_generator", sequenceName = "ingestion_flow_file_id_seq", allocationSize = 1)
  private Long ingestionFlowFileId;
  private Long organizationId;
  private String filePathName;
  private String fileName;
  private Long fileSize;
  @Enumerated(EnumType.STRING)
  private IngestionFlowFileType flowFileType;
  @Enumerated(EnumType.STRING)
  private IngestionFlowFileStatus status;
  private String codError;
  private String discardFileName;
  private Long numTotalRows;
  private Long numCorrectlyImportedRows;
  private Long pdfGenerated;
  private String pspIdentifier;
  private OffsetDateTime flowDateTime;
  private String fileOrigin;

}
