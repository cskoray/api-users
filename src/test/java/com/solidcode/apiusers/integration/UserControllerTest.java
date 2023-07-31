package com.solidcode.apiusers.integration;

import static com.google.common.io.Resources.getResource;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.google.common.io.Resources;
import com.solidcode.apiusers.adaptor.ZilchClient;
import com.solidcode.apiusers.adaptor.request.ZilchUserRequest;
import com.solidcode.apiusers.adaptor.response.ZilchUserResponse;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;

@SpringBootTest({"server.port:0", "eureka.client.enabled:false"})
class UserControllerTest {

  private static final String REGISTER_USER_TO_ZILCH_REQUEST = "request/ZilchUserRequest.json";
  private static final String REGISTER_USER_TO_ZILCH_RESPONSE = "response/ZilchResponse.json";

  private static ObjectMapper mapper = new ObjectMapper();

  @Autowired
  private ZilchClient zilchClient;

  @TestConfiguration
  public static class TestConfig {

    @Bean
    public ServiceInstanceListSupplier serviceInstanceListSupplier() {
      return new TestServiceInstanceListSupplier("ZILCH-PAY", 8888);
    }
  }

  @RegisterExtension
  static WireMockExtension ZILCH_PAY = WireMockExtension.newInstance()
      .options(WireMockConfiguration.wireMockConfig().port(8888))
      .build();

  @Test
  public void testZilchClient() throws IOException {

    String userRequest = Resources.toString(getResource(REGISTER_USER_TO_ZILCH_REQUEST), UTF_8);
    String userResponse = Resources.toString(getResource(REGISTER_USER_TO_ZILCH_RESPONSE), UTF_8);
    ZilchUserRequest userRegisterRequest = mapper.readValue(userRequest, ZilchUserRequest.class);
    ZilchUserResponse expected = mapper.readValue(userResponse, ZilchUserResponse.class);

    ZILCH_PAY.stubFor(WireMock.post("/v1/api/zilch/users/register")
        .withRequestBody(WireMock.equalToJson(userRequest))
        .willReturn(WireMock.okJson(userResponse)));

    ZilchUserResponse response = zilchClient.register(userRegisterRequest);

    assertThat(response).isNotNull();
    assertEquals(expected, response);
  }
}