package it.gov.pagopa.pu.processecexutions.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import it.gov.pagopa.pu.processecexutions.enums.ExportFlowFileType;
import it.gov.pagopa.pu.processecexutions.model.exportfile.*;
import jakarta.persistence.DiscriminatorValue;
import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ResolvableType;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
class ExportFileTest {

  private final Map<ExportFlowFileType, Pair<Class<? extends ExportFile<?>>, Class<? extends ExportFileFilter>>> expectedPolymorphicConfiguration = Map.of(
    ExportFlowFileType.CLASSIFICATIONS, Pair.of(ClassificationsExportFile.class, ClassificationsExportFileFilter.class),
    ExportFlowFileType.PAID, Pair.of(PaidExportFile.class, PaidExportFileFilter.class),
    ExportFlowFileType.PAYMENTS_REPORTING, Pair.of(PaymentsReportingExportFile.class, PaymentsReportingExportFileFilter.class)
  );

  @Test
  void testUpdatedExpectedMap() {
    Assertions.assertEquals(
      Arrays.stream(ExportFlowFileType.values())
        .collect(Collectors.toSet()),
      expectedPolymorphicConfiguration.keySet()
    );
  }

  @Test
  void testJsonPolymorphicConfiguration() {
    Assertions.assertEquals(expectedPolymorphicConfiguration,
      Arrays.stream(ExportFile.class.getAnnotation(JsonSubTypes.class).value())
        .collect(Collectors.toMap(
          t -> ExportFlowFileType.valueOf(t.name()),
          t -> Pair.of(
            (Class<? extends ExportFile<?>>) t.value(),
            (Class<? extends ExportFileFilter>) ResolvableType.forClass(t.value()).as(ExportFile.class).getGeneric(0).getRawClass()
          )))
    );
  }

  @Test
  void testJpaPolymorphicConfiguration() {
    expectedPolymorphicConfiguration.forEach((exportFlowFileType, exportFlowType2FilterClasses) -> {
      Class<? extends ExportFile<?>> exportFileClass = exportFlowType2FilterClasses.getLeft();
      Assertions.assertEquals(exportFlowFileType,
        ExportFlowFileType.valueOf(exportFileClass.getAnnotation(DiscriminatorValue.class).value()));
      try {
        Assertions.assertEquals(SqlTypes.JSON,
          exportFileClass.getDeclaredField("filterFields").getAnnotation(JdbcTypeCode.class).value());
      } catch (NoSuchFieldException ex) {
        throw new IllegalStateException(ex);
      }
    });
  }

  @Test
  void testOpenApiPolymorphicConfiguration(){
    expectedPolymorphicConfiguration.forEach((exportFlowFileType, exportFlowType2FilterClasses) -> {
      Class<? extends ExportFile<?>> exportFileClass = exportFlowType2FilterClasses.getLeft();
      try {
        Schema schemaAnnotation = exportFileClass.getDeclaredMethod("getFlowFileType").getAnnotation(Schema.class);
        Assertions.assertEquals("string", schemaAnnotation.type());
        Assertions.assertEquals(exportFlowFileType.name(), schemaAnnotation.allowableValues()[0]);
        Assertions.assertEquals(1, schemaAnnotation.allowableValues().length);
      } catch (NoSuchMethodException ex) {
        throw new IllegalStateException(ex);
      }
    });

    Assertions.assertEquals(
      expectedPolymorphicConfiguration.values().stream()
        .map(Pair::getRight)
        .collect(Collectors.toSet()),
      Arrays.stream(ExportFileFilter.class.getAnnotation(Schema.class).oneOf())
        .collect(Collectors.toSet())
    );
  }
}
