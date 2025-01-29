package it.gov.pagopa.pu.processecexutions.connector.workflowhub.config;

import it.gov.pagopa.pu.workflowhub.controller.ApiClient;
import it.gov.pagopa.pu.workflowhub.controller.BaseApi;
import it.gov.pagopa.pu.workflowhub.controller.generated.IngestionFlowApi;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Lazy
@Service
public class WorkflowHubApisHolder {

    private final IngestionFlowApi ingestionFlowApi;
    private final ThreadLocal<String> bearerTokenHolder = new ThreadLocal<>();

    public WorkflowHubApisHolder(
            @Value("${rest.workflow-hub.base-url}") String baseUrl,
            RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ApiClient apiClient = new ApiClient(restTemplate);
        apiClient.setBasePath(baseUrl);
        apiClient.setBearerToken(bearerTokenHolder::get);

        this.ingestionFlowApi = new IngestionFlowApi(apiClient);
    }

    @PreDestroy
    public void unload(){
        bearerTokenHolder.remove();
    }

    /** It will return a {@link IngestionFlowApi} instrumented with the provided accessToken. Use null if auth is not required */
    public IngestionFlowApi getIngestionFlowApi(String accessToken){
        return getApi(accessToken, ingestionFlowApi);
    }

    private <T extends BaseApi> T getApi(String accessToken, T api) {
        bearerTokenHolder.set(accessToken);
        return api;
    }
}
