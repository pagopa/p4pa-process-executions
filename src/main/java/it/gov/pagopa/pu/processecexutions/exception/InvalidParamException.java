package it.gov.pagopa.pu.processecexutions.exception;

public class InvalidParamException extends BaseBusinessException {
  public InvalidParamException(String code, String message) {
    super(code, message);
  }
}
