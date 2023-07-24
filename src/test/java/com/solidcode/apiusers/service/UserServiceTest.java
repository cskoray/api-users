package com.solidcode.apiusers.service;

import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.solidcode.apiusers.adaptor.ZilchClient;
import com.solidcode.apiusers.adaptor.request.ZilchUserRequest;
import com.solidcode.apiusers.adaptor.response.ZilchUserResponse;
import com.solidcode.apiusers.dto.request.UserRegisterRequest;
import com.solidcode.apiusers.dto.request.UserRequest;
import com.solidcode.apiusers.mapper.UserMapper;
import com.solidcode.apiusers.repository.UserRepository;
import com.solidcode.apiusers.repository.entity.User;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  private static final String KEY = UUID.randomUUID().toString();

  @InjectMocks
  private UserService unit;

  @Mock
  private UserRepository userRepository;

  @Mock
  private ZilchClient zilchClient;

  @Mock
  private UserMapper userMapper;

  @Test
  public void saveUser() {
    UserRequest userRequest = UserRequest.builder()
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
    when(userMapper.toUser(userRequest)).thenReturn(user);
    unit.saveUser(userRequest);

    ArgumentCaptor<User> userCapture = ArgumentCaptor.forClass(User.class);
    verify(userRepository).save(userCapture.capture());

    assertEquals(user.getName(), userCapture.getValue().getName());
    verify(userRepository, times(1)).save(user);
  }

  @Test
  public void applyZilch() {

    User user = User.builder()
        .userKey(KEY)
        .name("name")
        .email("a@a.com")
        .debitCardNumber("1111222233334444")
        .debitCvv("123")
        .debitExpiry("1228")
        .createdDate(Timestamp.valueOf(now()))
        .build();
    UserRegisterRequest userRegisterRequest = UserRegisterRequest.builder()
        .userKey(KEY)
        .build();
    ZilchUserRequest zilchUserRequest = ZilchUserRequest.builder()
        .name("name")
        .email("a@a.com")
        .cvv("123")
        .expiryDate("1228")
        .cardNumber("1111222233334444")
        .build();
    ZilchUserResponse zilchUserResponse = ZilchUserResponse.builder()
        .cardNumber("1111222233334444")
        .cvv("1228")
        .expiryDate("123")
        .paymentToken(UUID.randomUUID().toString())
        .creditLimit(new BigDecimal(100))
        .build();
    when(userRepository.findByUserKey(KEY)).thenReturn(ofNullable(user));
    when(userMapper.toZilchUserRequest(user)).thenReturn(zilchUserRequest);
    when(zilchClient.register(zilchUserRequest)).thenReturn(zilchUserResponse);

    unit.applyZilch(userRegisterRequest);

    assertEquals(zilchUserResponse.getPaymentToken(), user.getPaymentToken());
    assertEquals(zilchUserResponse.getCreditLimit(), user.getZilchCreditLimit());
    assertEquals(zilchUserResponse.getCardNumber(), user.getZilchCardNumber());
    assertEquals(zilchUserResponse.getCvv(), user.getZilchCvv());
    assertEquals(zilchUserResponse.getExpiryDate(), user.getZilchExpiry());
    verify(userRepository, times(1)).save(user);
  }

}