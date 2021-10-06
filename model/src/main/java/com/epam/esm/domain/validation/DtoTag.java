package com.epam.esm.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, TYPE_PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = DtoTagValidator.class)
public @interface DtoTag {
    String message() default ValidationErrorCode.INVALID_DTO_TAG;

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
