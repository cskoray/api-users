package com.solidcode.apiusers.dto.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CardValidator implements ConstraintValidator<ValidCardNumber, String> {

  @Override
  public void initialize(ValidCardNumber constraintAnnotation) {
  }

  @Override
  public boolean isValid(String cardNumber, ConstraintValidatorContext context) {

    int sum = 0;
    for (int i = cardNumber.length() - 1; i >= 0; i--) {
      int digit = Integer.parseInt(cardNumber.substring(i, i + 1));

      if ((cardNumber.length() - i) % 2 == 0) {
        digit = doubleAndSumDigits(digit);
      }

      sum += digit;
    }
    return sum % 10 == 0;
  }

  private static int doubleAndSumDigits(int digit) {

    int ret = digit * 2;
    if (ret > 9) {
      ret = digit - 9;
    }
    return ret;
  }
}