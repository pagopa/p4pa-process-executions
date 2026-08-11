package it.gov.pagopa.pu.processexecutions.exception;

import it.gov.pagopa.pu.processexecutions.exception.common.CommonExceptionHandlerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.doThrow;

class ProcessExecutionsExceptionHandlerTest extends CommonExceptionHandlerTest {


  @Test
  void handleExportFlowFileVersionNotSupportedException() throws Exception {
    doThrow(new ExportFlowFileVersionNotSupportedException("Unsupported version")).when(testControllerSpy).testEndpoint(DATA, BODY);

    performRequest(DATA, MediaType.APPLICATION_JSON)
      .andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("PROCESS_EXECUTIONS_INVALID_FILE_VERSION"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_FILE_VERSION"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Unsupported version"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.traceId").value(traceId));
  }

  @Test
  void handleInvalidParamException() throws Exception {
    doThrow(new InvalidParamException("ERRORCODE", "error")).when(testControllerSpy).testEndpoint(DATA, BODY);

    performRequest(DATA, MediaType.APPLICATION_JSON)
      .andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("PROCESS_EXECUTIONS_BAD_REQUEST"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("ERRORCODE"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("error"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.traceId").value(traceId));
  }

  @Test
  void handleInvalidTimeRangeException() throws Exception {
    doThrow(new InvalidTimeRangeException("error")).when(testControllerSpy).testEndpoint(DATA, BODY);

    performRequest(DATA, MediaType.APPLICATION_JSON)
      .andExpect(MockMvcResultMatchers.status().isBadRequest())
      .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("PROCESS_EXECUTIONS_INVALID_TIME_RANGE"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_DATE_FILTER_INTERVAL"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("error"))
      .andExpect(MockMvcResultMatchers.jsonPath("$.traceId").value(traceId));
  }

}
