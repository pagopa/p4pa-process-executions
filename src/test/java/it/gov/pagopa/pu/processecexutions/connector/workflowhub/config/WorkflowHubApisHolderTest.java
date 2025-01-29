package it.gov.pagopa.pu.processecexutions.connector.workflowhub.config;

import it.gov.pagopa.pu.processecexutions.connector.BaseApiHolderTest;
import it.gov.pagopa.pu.workflowhub.controller.ApiClient;
import it.gov.pagopa.pu.workflowhub.dto.generated.WorkflowCreatedDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.util.DefaultUriBuilderFactory;

@ExtendWith(MockitoExtension.class)
class WorkflowHubApisHolderTest extends BaseApiHolderTest {
  @Mock
  private RestTemplateBuilder restTemplateBuilderMock;

  private WorkflowHubApisHolder workflowHubApisHolder;

  @BeforeEach
  void setUp() {
    Mockito.when(restTemplateBuilderMock.build()).thenReturn(restTemplateMock);
    Mockito.when(restTemplateMock.getUriTemplateHandler()).thenReturn(new DefaultUriBuilderFactory());
    ApiClient apiClient = new ApiClient(restTemplateMock);
    String baseUrl = "http://example.com";
    apiClient.setBasePath(baseUrl);
    workflowHubApisHolder = new WorkflowHubApisHolder(baseUrl, restTemplateBuilderMock);
  }

  @AfterEach
  void verifyNoMoreInteractions() {
    Mockito.verifyNoMoreInteractions(
      restTemplateBuilderMock,
      restTemplateMock
    );
  }

  @Test
  void whenGetIngestionFlowApiThenAuthenticationShouldBeSetInThreadSafeMode() throws InterruptedException {
    assertAuthenticationShouldBeSetInThreadSafeMode(
      accessToken -> workflowHubApisHolder.getIngestionFlowApi(accessToken)
        .ingestPaymentsReportingFile(1L),
      WorkflowCreatedDTO.class,
      workflowHubApisHolder::unload);
  }
}
