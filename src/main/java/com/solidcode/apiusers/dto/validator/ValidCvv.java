package com.solidcode.apiusers.dto.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({PARAMETER, METHOD, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = CvvValidator.class)
@Documented
public @interface ValidCvv {

  String message() default "CVV must be 3 digits";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
