package com.postnord.ndm.api.common.validation.constraints;

import com.postnord.ndm.api.common.validation.validator.NdmStringRegexValidator;

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
@Constraint(validatedBy = NdmStringRegexValidator.class)
@Documented
public @interface NdmStringRegex {

    String DEFAULT_REGEX = "\\w+\\.?";

    String message() default "String is formatted incorrectly.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String regex() default DEFAULT_REGEX;

    boolean nullAllowed() default false;

    Response.Status status() default Response.Status.BAD_REQUEST;
}
