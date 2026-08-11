package it.gov.pagopa.pu.processexecutions.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.gov.pagopa.pu.processexecutions.enums.ExportFileStatus;
import it.gov.pagopa.pu.processexecutions.enums.ExportFileTypeEnum;
import it.gov.pagopa.pu.processexecutions.model.exportfile.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "export_file")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "exportFileType")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "exportFileType")
@JsonSubTypes({
  @JsonSubTypes.Type(name = "CLASSIFICATIONS", value = ClassificationsExportFile.class),
  @JsonSubTypes.Type(name = "PAID", value = PaidExportFile.class),
  @JsonSubTypes.Type(name = "PAYMENTS_REPORTING", value = PaymentsReportingExportFile.class),
  @JsonSubTypes.Type(name = "RECEIPTS_ARCHIVING", value = ReceiptsArchivingExportFile.class)
})
public abstract class ExportFile <T extends ExportFileFilter> extends BaseEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "export_file_generator")
  @SequenceGenerator(name = "export_file_generator", sequenceName = "export_file_id_seq", allocationSize = 1)
  private Long exportFileId;
  @NotNull
  private Long organizationId;
  @NotNull
  private String operatorExternalId;
  private String filePathName;
  private String fileName;
  private Long fileSize;
  @Enumerated(EnumType.STRING)
  @Column(insertable = false, updatable = false)
  private ExportFileTypeEnum exportFileType;
  @NotNull
  private String fileVersion;
  @Enumerated(EnumType.STRING)
  @NotNull
  private ExportFileStatus status;
  private String errorDescription;
  private Long numTotalRows;
  private OffsetDateTime expirationDate;

  public abstract T getFilterFields();
  public abstract void setFilterFields(T filterFields);
}
