package it.gov.pagopa.pu.processecexutions.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocalDateIntervalFilter implements Serializable {
  private LocalDate from;
  private LocalDate to;
}
