package it.gov.pagopa.pu.processexecutions.exception;

import it.gov.pagopa.pu.processexecutions.exception.common.BaseBusinessException;

public class InvalidParamException extends BaseBusinessException {
  public InvalidParamException(String code, String message) {
    super(code, message);
  }
}
