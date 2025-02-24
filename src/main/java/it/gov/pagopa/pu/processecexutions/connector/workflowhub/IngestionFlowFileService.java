package it.gov.pagopa.pu.processecexutions.connector.workflowhub;

import it.gov.pagopa.pu.processecexutions.model.IngestionFlowFile;

public interface IngestionFlowFileService {
  void invokeIngestionWorkflow(IngestionFlowFile ingestionFlowFile, String accessToken);
}
