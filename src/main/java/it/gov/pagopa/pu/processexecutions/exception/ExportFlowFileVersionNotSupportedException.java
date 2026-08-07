package it.gov.pagopa.pu.processexecutions.exception;

import it.gov.pagopa.pu.processexecutions.exception.common.BaseBusinessException;
import it.gov.pagopa.pu.processexecutions.util.ErrorCodeConstants;

public class ExportFlowFileVersionNotSupportedException extends BaseBusinessException {

  public ExportFlowFileVersionNotSupportedException(String message){
    super(ErrorCodeConstants.ERROR_CODE_INVALID_FILE_VERSION, message);
  }
}
