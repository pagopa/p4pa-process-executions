package it.gov.pagopa.pu.processecexutions.util;

import it.gov.pagopa.pu.processecexutions.dto.LocalDateIntervalFilter;
import it.gov.pagopa.pu.processecexutions.dto.OffsetDateTimeIntervalFilter;
import it.gov.pagopa.pu.processecexutions.exception.InvalidTimeRangeException;
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

  public static String getSpanId(){
    return MDC.get("spanId");
  }

  public static boolean isValidIntervalBetweenOffsetDateTime(OffsetDateTime from, OffsetDateTime to, String filterName, ChronoUnit unit, long maxInterval) {
    validateDateFilters(new OffsetDateTimeIntervalFilter(from, to), filterName);
    return unit.between(from, to) <= maxInterval;
  }

  public static boolean isValidIntervalBetweenLocalDate(LocalDate from, LocalDate to, String filterName, ChronoUnit unit, long maxInterval) {
    validateDateFilters(new LocalDateIntervalFilter(from, to), filterName);
    return unit.between(from, to) <= maxInterval;
  }

  public static boolean validateDateFilters(LocalDateIntervalFilter dateFilter, String filterName) {
    if ((dateFilter.getFrom() != null ^ dateFilter.getTo() != null)) {
      throw new InvalidTimeRangeException("Both " + filterName + "From and " + filterName + "To must be set or both must be null");
    }
    if(isFromAfterTo(dateFilter.getFrom(), dateFilter.getTo())){
      throw new InvalidTimeRangeException(filterName + "To must be after " + filterName + "From");
    }
    return true;
  }

  public static boolean validateDateFilters(OffsetDateTimeIntervalFilter dateFilter, String filterName) {
    if ((dateFilter.getFrom() != null ^ dateFilter.getTo() != null)) {
      throw new InvalidTimeRangeException("Both " + filterName + "From and " + filterName + "To must be set or both must be null");
    }
    if(isFromAfterTo(dateFilter.getFrom(), dateFilter.getTo())){
      throw new InvalidTimeRangeException(filterName + "To must be after " + filterName + "From");
    }
    return true;
  }

  public static boolean isDateFilterConfigured(OffsetDateTimeIntervalFilter dateFilter, String filterName) {
    if (dateFilter == null) {
      return false;
    }
    Utilities.validateDateFilters(dateFilter, filterName);
    return dateFilter.getFrom() != null;
  }

  public static boolean isFromAfterTo(OffsetDateTime from, OffsetDateTime to) {
    return from != null && to != null && from.isAfter(to);
  }

  public static boolean isFromAfterTo(LocalDate from, LocalDate to) {
    return from != null && to != null && from.isAfter(to);
  }
}
