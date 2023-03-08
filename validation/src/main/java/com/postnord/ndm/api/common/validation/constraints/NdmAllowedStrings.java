package com.postnord.ndm.api.common.validation.constraints;

import com.postnord.ndm.api.common.validation.validator.NdmAllowedStringsValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.ws.rs.core.Response;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER, TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NdmAllowedStringsValidator.class)
@Documented
public @interface NdmAllowedStrings {

    String DEFAULT_MESSAGE = "Value not allowed.";

    String message() default DEFAULT_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] values();

    boolean nullAllowed() default false;

    Response.Status status() default Response.Status.BAD_REQUEST;
}
