package it.gov.pagopa.pu.processecexutions.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.gov.pagopa.pu.processecexutions.enums.ExportFileStatus;
import it.gov.pagopa.pu.processecexutions.enums.ExportFlowFileType;
import it.gov.pagopa.pu.processecexutions.model.exportfile.ClassificationExportFile;
import it.gov.pagopa.pu.processecexutions.model.exportfile.ExportFileFilter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "export_file")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "flowFileType")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "flowFileType")
@JsonSubTypes({
  @JsonSubTypes.Type(name = "CLASSIFICATIONS", value = ClassificationExportFile.class)
})
public class ExportFile <T extends ExportFileFilter> extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "export_file_generator")
  @SequenceGenerator(name = "export_file_generator", sequenceName = "export_file_id_seq", allocationSize = 1)
  private Long exportFileId;
  private Long organizationId;
  private String operatorExternalId;
  private String filePathName;
  private String fileName;
  private Long fileSize;
  @Transient
  private ExportFlowFileType flowFileType;
  @JdbcTypeCode(SqlTypes.JSON)
  private T filterFields;
  @Enumerated(EnumType.STRING)
  private ExportFileStatus status;
  private String codError;
  private Long numTotalRows;
  private OffsetDateTime expirationDate;
}
