package it.gov.pagopa.pu.processecexutions.exception;

import it.gov.pagopa.pu.processecexutions.util.ErrorCodeConstants;

public class ExportFlowFileVersionNotSupportedException extends BaseBusinessException {

  public ExportFlowFileVersionNotSupportedException(String message){
    super(ErrorCodeConstants.ERROR_CODE_INVALID_FILE_VERSION, message);
  }
}
