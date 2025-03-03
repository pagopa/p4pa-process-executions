package it.gov.pagopa.pu.processecexutions.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.net.URI;

public class SecurityUtilsTest {

  @BeforeEach
  @AfterEach
  void clear() {
    SecurityContextHolder.clearContext();
  }

  //region getAccessToken
  @Test
  void givenNoSecurityContextWhenGetAccessTokenThenNull() {
    Assertions.assertNull(SecurityUtils.getAccessToken());
  }

  @Test
  void givenNoAuthenticationWhenGetAccessTokenThenNull() {
    SecurityContextHolder.setContext(new SecurityContextImpl());
    Assertions.assertNull(SecurityUtils.getAccessToken());
  }

  @Test
  void givenJwtWhenGetAccessTokenThenReturnToken() {
    // Given
    Jwt jwt = configureSecurityContext("OPERATOREXTERNALID");

    // When
    String result = SecurityUtils.getAccessToken();

    // Then
    Assertions.assertSame(jwt.getTokenValue(), result);
  }
//endregion

  @Test
  void givenJwtWhenGetCurrentUserExternalIdThenReturnPrincipalName() {
    // Given
    String principalName = "PRINCIPALNAME";
    configureSecurityContext(principalName);

    // When
    String result = SecurityUtils.getCurrentUserExternalId();

    // Then
    Assertions.assertSame(principalName, result);
  }

  public static Jwt configureSecurityContext(String operatorExternalId) {
    Jwt jwt = Jwt
      .withTokenValue("TOKENHEADER.TOKENPAYLOAD.TOKENDIGEST")
      .header("", "")
      .claim("", "")
      .build();
    SecurityContextHolder.setContext(new SecurityContextImpl(new JwtAuthenticationToken(jwt, null, operatorExternalId)));
    return jwt;
  }

  @Test
  void givenUriWhenRemovePiiFromURIThenOk(){
    String result = SecurityUtils.removePiiFromURI(URI.create("https://host/path?param1=PII&param2=noPII"));
    Assertions.assertEquals("https://host/path?param1=***&param2=***", result);
  }

  @Test
  void givenNullUriWhenRemovePiiFromURIThenOk(){
    Assertions.assertNull(SecurityUtils.removePiiFromURI(null));
  }
}
