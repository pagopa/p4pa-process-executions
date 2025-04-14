package it.gov.pagopa.pu.processecexutions.connector.workflowhub.config;

import it.gov.pagopa.pu.processecexutions.connector.BaseApiHolderTest;
import it.gov.pagopa.pu.processecexutions.enums.ExportFileTypeEnum;
import it.gov.pagopa.pu.processecexutions.enums.IngestionFlowFileTypeEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
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
    WorkflowHubApiClientConfig clientConfig = WorkflowHubApiClientConfig.builder()
      .baseUrl("http://example.com")
      .build();
    workflowHubApisHolder = new WorkflowHubApisHolder(clientConfig, restTemplateBuilderMock);
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
        .ingestFlowFile(1L, IngestionFlowFileTypeEnum.PAYMENTS_REPORTING),
      new ParameterizedTypeReference<>() {},
      workflowHubApisHolder::unload);
  }

  @Test
  void whenGetExportFileThenAuthenticationShouldBeSetInThreadSafeMode() throws InterruptedException {
    assertAuthenticationShouldBeSetInThreadSafeMode(
      accessToken -> workflowHubApisHolder.getExportFileApi(accessToken)
        .exportFile(1L, ExportFileTypeEnum.PAID),
      new ParameterizedTypeReference<>() {},
      workflowHubApisHolder::unload);
  }
}
