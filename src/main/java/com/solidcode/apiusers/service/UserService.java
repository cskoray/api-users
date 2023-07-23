package com.solidcode.apiusers.service;

import static com.solidcode.apiusers.exception.ErrorType.USER_NOT_FOUND;
import static java.time.LocalDateTime.now;

import com.solidcode.apiusers.adaptor.ZilchClient;
import com.solidcode.apiusers.adaptor.request.ZilchUserRequest;
import com.solidcode.apiusers.adaptor.response.ZilchUserResponse;
import com.solidcode.apiusers.dto.request.UserRegisterRequest;
import com.solidcode.apiusers.dto.request.UserRequest;
import com.solidcode.apiusers.exception.UserNotFoundException;
import com.solidcode.apiusers.mapper.UserMapper;
import com.solidcode.apiusers.repository.UserRepository;
import com.solidcode.apiusers.repository.entity.User;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RefreshScope
public class UserService {

  private UserRepository userRepository;
  private ZilchClient zilchClient;
  private UserMapper userMapper;

  @Autowired
  public UserService(UserRepository userRepository, ZilchClient zilchClient,
      UserMapper userMapper) {
    this.userRepository = userRepository;
    this.zilchClient = zilchClient;
    this.userMapper = userMapper;
  }

  public User saveUser(@Valid UserRequest request) {

    log.info("UserService: saveUser {}", request.toString());
    User user = userMapper.toUser(request);
    user.setCreatedDate(Timestamp.valueOf(now()));
    user.setUserKey(UUID.randomUUID().toString());
    return userRepository.save(user);
  }

  @Cacheable("user")
  public User getUser(Long id) {

    log.info("UserService: getUser id: {}", id);
    return userRepository.findById(id).orElseThrow();
  }

  public User updateUserCreditLimit(String paymentToken, BigDecimal creditLimit) {

    log.info("UserService: updateUserCreditLimit, paymentToken: {}", paymentToken);
    User userDb = userRepository.findByPaymentToken(paymentToken)
        .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND, "id"));
    userDb.setZilchCreditLimit(userDb.getZilchCreditLimit().add(creditLimit));
    return userRepository.save(userDb);
  }

  public ZilchUserResponse applyZilch(UserRegisterRequest userRegisterRequest) {

    User user = userRepository.findByUserKey(userRegisterRequest.getUserKey())
        .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND, "userKey"));
    ZilchUserRequest zilchUserRequest = userMapper.toZilchUserRequest(user);
    log.info("UserService: applyZilch, {}", zilchUserRequest.toString());
    ZilchUserResponse zilchUserResponse = zilchClient.register(zilchUserRequest);

    user.setZilchCardNumber(zilchUserResponse.getCardNumber());
    user.setZilchCvv(zilchUserResponse.getCvv());
    user.setZilchExpiry(zilchUserResponse.getExpiryDate());
    user.setZilchCreditLimit(zilchUserResponse.getCreditLimit());
    user.setPaymentToken(zilchUserResponse.getPaymentToken());
    userRepository.save(user);
    return zilchUserResponse;
  }
}
