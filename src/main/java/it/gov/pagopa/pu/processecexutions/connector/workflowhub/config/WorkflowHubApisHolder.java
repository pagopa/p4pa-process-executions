package it.gov.pagopa.pu.processecexutions.connector.workflowhub.config;

import it.gov.pagopa.pu.processecexutions.config.rest.RestTemplateConfig;
import it.gov.pagopa.pu.workflowhub.controller.ApiClient;
import it.gov.pagopa.pu.workflowhub.controller.BaseApi;
import it.gov.pagopa.pu.workflowhub.controller.generated.ExportFileApi;
import it.gov.pagopa.pu.workflowhub.controller.generated.IngestionFlowApi;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Lazy
@Service
public class WorkflowHubApisHolder {

    private final IngestionFlowApi ingestionFlowApi;
    private final ExportFileApi exportFileApi;
    private final ThreadLocal<String> bearerTokenHolder = new ThreadLocal<>();

    public WorkflowHubApisHolder(
      WorkflowHubApiClientConfig clientConfig,
      RestTemplateBuilder restTemplateBuilder
    ) {
      RestTemplate restTemplate = restTemplateBuilder.build();
        ApiClient apiClient = new ApiClient(restTemplate);
      apiClient.setBasePath(clientConfig.getBaseUrl());
      apiClient.setBearerToken(bearerTokenHolder::get);
      apiClient.setMaxAttemptsForRetry(Math.max(1, clientConfig.getMaxAttempts()));
      apiClient.setWaitTimeMillis(clientConfig.getWaitTimeMillis());
      if (clientConfig.isPrintBodyWhenError()) {
        restTemplate.setErrorHandler(RestTemplateConfig.bodyPrinterWhenError("WORKFLOW-HUB"));
      }

        this.ingestionFlowApi = new IngestionFlowApi(apiClient);
      this.exportFileApi = new ExportFileApi(apiClient);
    }

    @PreDestroy
    public void unload(){
        bearerTokenHolder.remove();
    }

    /** It will return a {@link IngestionFlowApi} instrumented with the provided accessToken. Use null if auth is not required */
    public IngestionFlowApi getIngestionFlowApi(String accessToken){
        return getApi(accessToken, ingestionFlowApi);
    }

  /** It will return a {@link ExportFileApi} instrumented with the provided accessToken. Use null if auth is not required */
    public ExportFileApi getExportFileApi(String accessToken){
      return getApi(accessToken, exportFileApi);
    }

    private <T extends BaseApi> T getApi(String accessToken, T api) {
        bearerTokenHolder.set(accessToken);
        return api;
    }
}
