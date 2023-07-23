package com.solidcode.apiusers.dto.validator;

import static java.lang.Integer.MIN_VALUE;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CvvValidator implements ConstraintValidator<ValidCvv, String> {

  @Override
  public void initialize(ValidCvv constraintAnnotation) {
  }

  @Override
  public boolean isValid(String cvv, ConstraintValidatorContext context) {
    return (validateCvv(cvv));
  }

  private boolean validateCvv(String cvv) {
    return Integer.parseInt(cvv) > MIN_VALUE && cvv.length() == 3;
  }
}