package com.solidcode.apiusers.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.solidcode.apiusers.adaptor.response.ZilchUserResponse;
import com.solidcode.apiusers.dto.request.CreditLimitRequest;
import com.solidcode.apiusers.dto.request.UserRegisterRequest;
import com.solidcode.apiusers.dto.request.UserRequest;
import com.solidcode.apiusers.repository.entity.User;
import com.solidcode.apiusers.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/api/users")
@Validated
public class UserController {

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  @ResponseStatus(CREATED)
  public User saveUser(@Valid @RequestBody UserRequest request) {

    log.info("UserController: saveUser {}", request.toString());
    return userService.saveUser(request);
  }

  @GetMapping("/user/{id}")
  @ResponseStatus(OK)
  public User getUser(@PathVariable("id") Long id) {

    log.info("UserController: getUser id: {}", id);
    return userService.getUser(id);
  }

  @PutMapping("/user/credit-limit")
  @ResponseStatus(OK)
  public User updateUserCreditLimit(@Valid @RequestBody CreditLimitRequest request) {

    log.info("UserController: updateUser paymentToken: {}, amount {}", request.getPaymentToken(),
        request.getAmount());
    return userService.updateUserCreditLimit(request.getPaymentToken(), request.getAmount());
  }

  @PostMapping("/zilch/register")
  @ResponseStatus(CREATED)
  public ZilchUserResponse applyZilch(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {

    log.info("UserController: applyZilch {}", userRegisterRequest.toString());
    return userService.applyZilch(userRegisterRequest);
  }
}
