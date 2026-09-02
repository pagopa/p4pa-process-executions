package it.gov.pagopa.pu.processexecutions.exception.transcoder.handler;

import it.gov.pagopa.pu.processexecutions.dto.generated.ProcessExecutionsErrorDTO;
import it.gov.pagopa.pu.processexecutions.dto.generated.ErrorFieldDTO;
import it.gov.pagopa.pu.processexecutions.exception.transcoder.ExceptionMessageTranscoded;
import it.gov.pagopa.pu.processexecutions.exception.transcoder.ExceptionMessageTranscoder;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.List;

public class MissingServletRequestParameterExceptionMessageTranscoder implements ExceptionMessageTranscoder<MissingServletRequestParameterException> {

  @Override
  public ExceptionMessageTranscoded transcode(MissingServletRequestParameterException missingServletRequestParameterException) {
    return new ExceptionMessageTranscoded(
      ProcessExecutionsErrorDTO.CategoryEnum.PROCESS_EXECUTIONS_BAD_REQUEST.getValue(),
      missingServletRequestParameterException.getMessage(),
      List.of(new ErrorFieldDTO(missingServletRequestParameterException.getParameterName(), "NotNull", missingServletRequestParameterException.getMessage())));
  }
}
