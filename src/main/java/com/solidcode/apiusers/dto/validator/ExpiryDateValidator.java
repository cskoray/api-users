package com.solidcode.apiusers.dto.validator;

import static java.lang.Integer.MIN_VALUE;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExpiryDateValidator implements ConstraintValidator<ValidExpiryDate, String> {

  @Override
  public void initialize(ValidExpiryDate constraintAnnotation) {
  }

  @Override
  public boolean isValid(String expiry, ConstraintValidatorContext context) {
    return (validateExpiry(expiry));
  }

  private boolean validateExpiry(String expiry) {
    return Integer.parseInt(expiry) > MIN_VALUE && expiry.length() == 4;
  }
}