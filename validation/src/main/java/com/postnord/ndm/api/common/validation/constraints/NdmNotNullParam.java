package com.postnord.ndm.api.common.validation.constraints;

import com.postnord.ndm.api.common.validation.validator.NdmNotNullParamValidator;

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
@Constraint(validatedBy = NdmNotNullParamValidator.class)
@Documented
public @interface NdmNotNullParam {

    String message() default "Parameter cannot be null.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Response.Status status() default Response.Status.BAD_REQUEST;
}