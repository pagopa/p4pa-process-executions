package it.gov.pagopa.pu.processecexutions.util;

import org.slf4j.MDC;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

public class Utilities {
  private Utilities() {
  }

  public static String getTraceId() {
    return MDC.get("traceId");
  }

  public static boolean isValidIntervalBetweenOffsetDateTime(OffsetDateTime from, OffsetDateTime to, ChronoUnit unit, long maxInterval) {
    if (from == null || to == null || unit == null) return false;
    return unit.between(from, to) <= maxInterval;
  }

  public static boolean isValidIntervalBetweenLocalDate(LocalDate from, LocalDate to, ChronoUnit unit, long maxInterval) {
    if (from == null || to == null || unit == null) return false;
    return unit.between(from, to) <= maxInterval;
  }

}
