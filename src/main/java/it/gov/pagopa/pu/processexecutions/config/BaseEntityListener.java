package it.gov.pagopa.pu.processexecutions.config;

import it.gov.pagopa.pu.processexecutions.model.BaseEntity;
import it.gov.pagopa.pu.processexecutions.util.Utilities;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class BaseEntityListener {

  @PrePersist
  public void onPrePersist(BaseEntity entity) {
    onSave(entity);
  }

  @PreUpdate
  public void onPreUpdate(BaseEntity entity) {
    onSave(entity);
  }

  private void onSave(BaseEntity entity) {
    entity.setUpdateTraceId(Utilities.getTraceId());
  }
}
