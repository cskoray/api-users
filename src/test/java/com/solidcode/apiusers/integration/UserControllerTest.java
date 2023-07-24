package com.solidcode.apiusers.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.solidcode.apiusers.dto.request.UserRequest;
import com.solidcode.apiusers.repository.UserRepository;
import com.solidcode.apiusers.repository.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerTest {

  private static TestRestTemplate restTemplate;
  private String baseUrl = "http://localhost";
  private static UserRequest userRequest;

  @Autowired
  private UserRepository userRepository;

  @LocalServerPort
  private int port;

  @BeforeAll
  public static void beforeClass() {
    restTemplate = new TestRestTemplate();
  }

  @BeforeEach
  public void setUp() {
    userRequest = UserRequest.builder()
        .name("some name")
        .email("a@a.com")
        .cardNumber("1111222233334444")
        .cvv("123")
        .expiryDate("1228")
        .build();
    baseUrl = baseUrl + ":" + port + "/v1/api/users";
  }

  @AfterEach
  public void tearDown() {
    userRepository.deleteAll();
  }

  @Test
  public void saveUser() {

    User response = restTemplate.postForObject(baseUrl + "/register", userRequest, User.class);
    User first = userRepository.findAll().stream().findFirst().get();

    assertEquals(response.getId(), first.getId());
    assertEquals(response.getName(), first.getName());
    assertEquals(response.getEmail(), first.getEmail());
    assertEquals(response.getDebitCardNumber(), first.getDebitCardNumber());
    assertEquals(response.getDebitCvv(), first.getDebitCvv());
    assertEquals(response.getDebitExpiry(), first.getDebitExpiry());
    assertEquals(1, userRepository.findAll().size());
  }
}