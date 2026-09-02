package it.gov.pagopa.pu.processexecutions.exception;

import it.gov.pagopa.pu.processexecutions.exception.common.BaseBusinessException;
import it.gov.pagopa.pu.processexecutions.util.ErrorCodeConstants;

public class InvalidTimeRangeException extends BaseBusinessException {

  public InvalidTimeRangeException(String message) {
    super(ErrorCodeConstants.ERROR_CODE_INVALID_DATE_FILTER_INTERVAL, message);
  }
}
