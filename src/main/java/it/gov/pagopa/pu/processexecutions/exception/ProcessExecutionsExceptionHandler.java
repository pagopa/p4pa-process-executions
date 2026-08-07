package it.gov.pagopa.pu.processexecutions.exception;

import it.gov.pagopa.pu.processexecutions.dto.generated.ProcessExecutionsErrorDTO;
import it.gov.pagopa.pu.processexecutions.exception.common.CommonExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ProcessExecutionsExceptionHandler extends CommonExceptionHandler {

  @ExceptionHandler({ExportFlowFileVersionNotSupportedException.class})
  public ResponseEntity<ProcessExecutionsErrorDTO> handleBadRequestExceptions(Exception ex, HttpServletRequest request) {
    return handleException(ex, request, HttpStatus.BAD_REQUEST, ProcessExecutionsErrorDTO.CategoryEnum.PROCESS_EXECUTIONS_INVALID_FILE_VERSION);
  }

  @ExceptionHandler({InvalidParamException.class})
  public ResponseEntity<ProcessExecutionsErrorDTO> handleInvalidParamException(Exception ex, HttpServletRequest request) {
    return handleException(ex, request, HttpStatus.BAD_REQUEST, ProcessExecutionsErrorDTO.CategoryEnum.PROCESS_EXECUTIONS_BAD_REQUEST);
  }

  @ExceptionHandler({InvalidTimeRangeException.class})
  public ResponseEntity<ProcessExecutionsErrorDTO> handleInvalidTimeRangeException(Exception ex, HttpServletRequest request) {
    return handleException(ex, request, HttpStatus.BAD_REQUEST, ProcessExecutionsErrorDTO.CategoryEnum.PROCESS_EXECUTIONS_INVALID_TIME_RANGE);
  }

}
