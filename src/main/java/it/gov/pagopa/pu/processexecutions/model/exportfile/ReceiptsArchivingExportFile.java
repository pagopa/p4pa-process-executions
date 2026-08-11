package it.gov.pagopa.pu.processexecutions.model.exportfile;

import io.swagger.v3.oas.annotations.media.Schema;
import it.gov.pagopa.pu.processexecutions.enums.ExportFileTypeEnum;
import it.gov.pagopa.pu.processexecutions.model.ExportFile;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("RECEIPTS_ARCHIVING")
public class ReceiptsArchivingExportFile extends ExportFile<ReceiptsArchivingExportFileFilter> {

  public ReceiptsArchivingExportFile(){
    setExportFileType(ExportFileTypeEnum.RECEIPTS_ARCHIVING);
  }

  @Schema(type = "string", allowableValues = "RECEIPTS_ARCHIVING")
  @Override
  public ExportFileTypeEnum getExportFileType() {
    return super.getExportFileType();
  }

  @JdbcTypeCode(SqlTypes.JSON)
  private ReceiptsArchivingExportFileFilter filterFields;
}
