package it.gov.pagopa.pu.processecexutions.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import it.gov.pagopa.pu.processecexutions.config.json.LocalDateTimeToOffsetDateTimeSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@SuperBuilder(toBuilder = true)
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {
  @Column(updatable = false)
  @CreatedDate
  @JsonSerialize(using = LocalDateTimeToOffsetDateTimeSerializer.class)
  private LocalDateTime creationDate;
  @LastModifiedDate
  @JsonSerialize(using = LocalDateTimeToOffsetDateTimeSerializer.class)
  private LocalDateTime updateDate;
  @LastModifiedBy
  private String updateOperatorExternalId;
}
