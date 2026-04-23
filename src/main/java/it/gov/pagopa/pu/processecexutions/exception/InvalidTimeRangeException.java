package it.gov.pagopa.pu.processecexutions.exception;

import it.gov.pagopa.pu.processecexutions.util.ErrorCodeConstants;

public class InvalidTimeRangeException extends BaseBusinessException {

  public InvalidTimeRangeException(String message) {
    super(ErrorCodeConstants.ERROR_CODE_INVALID_DATE_FILTER_INTERVAL, message);
  }
}
