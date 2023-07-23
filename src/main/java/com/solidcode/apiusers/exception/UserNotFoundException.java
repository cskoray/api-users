package com.solidcode.apiusers.exception;


import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

  private ErrorType errorType;
  private String field;

  public UserNotFoundException(ErrorType errorType, String field) {
    super(errorType.getMessage());
    this.errorType = errorType;
    this.field = field;
  }
}
