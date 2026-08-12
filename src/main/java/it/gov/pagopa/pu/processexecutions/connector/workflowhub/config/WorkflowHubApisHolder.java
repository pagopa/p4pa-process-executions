package it.gov.pagopa.pu.processexecutions.connector.workflowhub.config;

import it.gov.pagopa.pu.processexecutions.config.rest.HttpClientErrorJsonBodyHandler;
import it.gov.pagopa.pu.processexecutions.connector.workflowhub.mapper.WorkflowErrorDTOMapper;
import it.gov.pagopa.pu.workflowhub.generated.ApiClient;
import it.gov.pagopa.pu.workflowhub.generated.BaseApi;
import it.gov.pagopa.pu.workflowhub.client.generated.ExportFileApi;
import it.gov.pagopa.pu.workflowhub.client.generated.IngestionFlowApi;
import it.gov.pagopa.pu.workflowhub.dto.generated.WorkflowErrorDTO;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.json.JsonMapper;

@Lazy
@Service
public class WorkflowHubApisHolder {

    private final IngestionFlowApi ingestionFlowApi;
    private final ExportFileApi exportFileApi;
    private final ThreadLocal<String> bearerTokenHolder = new ThreadLocal<>();

    public WorkflowHubApisHolder(
      WorkflowHubApiClientConfig clientConfig,
      RestTemplateBuilder restTemplateBuilder,
      JsonMapper jsonMapper
    ) {
      RestTemplate restTemplate = restTemplateBuilder.build();
        ApiClient apiClient = new ApiClient(restTemplate);
      apiClient.setBasePath(clientConfig.getBaseUrl());
      apiClient.setBearerToken(bearerTokenHolder::get);
      apiClient.setMaxAttemptsForRetry(Math.max(1, clientConfig.getMaxAttempts()));
      apiClient.setWaitTimeMillis(clientConfig.getWaitTimeMillis());
      restTemplate.setErrorHandler(new HttpClientErrorJsonBodyHandler<>(jsonMapper, "WORKFLOW-HUB", clientConfig.isPrintBodyWhenError(),
        WorkflowErrorDTO.class, WorkflowErrorDTOMapper::map)
      );

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
