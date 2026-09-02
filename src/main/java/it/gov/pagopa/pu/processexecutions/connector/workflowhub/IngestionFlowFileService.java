package it.gov.pagopa.pu.processexecutions.connector.workflowhub;

import it.gov.pagopa.pu.processexecutions.model.IngestionFlowFile;

public interface IngestionFlowFileService {
  void invokeIngestionWorkflow(IngestionFlowFile ingestionFlowFile, String accessToken);
}
