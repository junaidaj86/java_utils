package com.postnord.ndm.api.common.validation.constraints;

import com.postnord.ndm.api.common.validation.validator.NdmStringLengthValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.ws.rs.core.Response;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER, TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NdmStringLengthValidator.class)
@Documented
public @interface NdmStringLength {
    int DEFAULT_LENGTH = 160;
    int DEFAULT_MIN_LENGTH = 1;

    String message() default "String size is too small or too large.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int length() default DEFAULT_LENGTH;

    int minLength() default DEFAULT_MIN_LENGTH;

    boolean nullAllowed() default false;

    Response.Status status() default Response.Status.BAD_REQUEST;
}
