package it.gov.pagopa.pu.processexecutions.exception.transcoder.handler;

import it.gov.pagopa.pu.processexecutions.dto.generated.ProcessExecutionsErrorDTO;
import it.gov.pagopa.pu.processexecutions.exception.transcoder.ExceptionMessageTranscoded;
import it.gov.pagopa.pu.processexecutions.exception.transcoder.ExceptionMessageTranscoder;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

public class DataIntegrityViolationExceptionMessageTranscoder implements ExceptionMessageTranscoder<DataIntegrityViolationException> {

  @Override
  public ExceptionMessageTranscoded transcode(DataIntegrityViolationException dataIntegrityViolationException) {
    String errorMsg = "Conflict.";
    if(dataIntegrityViolationException.getCause() instanceof ConstraintViolationException hibernateConstraintViolationException) {
      errorMsg += " " + hibernateConstraintViolationException.getSQLException().getMessage();
    }
    return new ExceptionMessageTranscoded(
      ProcessExecutionsErrorDTO.CategoryEnum.PROCESS_EXECUTIONS_CONFLICT.getValue(),
      errorMsg,
      null) ;
  }
}
