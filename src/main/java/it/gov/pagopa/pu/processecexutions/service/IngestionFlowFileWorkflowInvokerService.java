package it.gov.pagopa.pu.processecexutions.service;

import it.gov.pagopa.pu.processecexutions.model.IngestionFlowFile;
import org.springframework.stereotype.Service;

@Service
public class IngestionFlowFileWorkflowInvokerService {

  void invokeIngestionWorkflow(IngestionFlowFile ingestionFlowFile){
    // TODO call workflowHub based on flowFileType (P4ADEV-1813, P4ADEV-1814), print returned workflowId
  }

}
