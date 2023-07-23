package com.solidcode.apiusers.dto.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({PARAMETER, METHOD, FIELD, LOCAL_VARIABLE})
@Retention(RUNTIME)
@Constraint(validatedBy = UUIDValidator.class)
@Documented
public @interface ValidUUID {

  String message() default "Invalid UUID";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
