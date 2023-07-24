package com.solidcode.apiusers.controller;

import static java.time.LocalDateTime.now;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solidcode.apiusers.dto.request.UserRequest;
import com.solidcode.apiusers.repository.entity.User;
import com.solidcode.apiusers.service.UserService;
import java.sql.Timestamp;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
class UserControllerTest {

  private static final String KEY = UUID.randomUUID().toString();

  @MockBean
  private UserService userService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void saveUser() throws Exception {

    UserRequest request = UserRequest.builder()
        .name("some name")
        .email("a@a.com")
        .cardNumber("1111222233334444")
        .cvv("123")
        .expiryDate("1228")
        .build();
    User user = User.builder()
        .userKey(KEY)
        .name("name")
        .email("a@a.com")
        .debitCardNumber("1111222233334444")
        .debitCvv("123")
        .debitExpiry("1228")
        .createdDate(Timestamp.valueOf(now()))
        .build();
    when(userService.saveUser(any(UserRequest.class))).thenReturn(user);

    mockMvc.perform(post("/v1/api/users/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name").value(user.getName()))
        .andExpect(jsonPath("$.email").value(user.getEmail()));
  }
}