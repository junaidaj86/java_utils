package com.postnord.ndm.api.common.validation.constraints;

import com.postnord.ndm.api.common.validation.validator.NdmListLengthValidator;

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
@Constraint(validatedBy = NdmListLengthValidator.class)
@Documented
public @interface NdmListLength {

    int DEFAULT_LENGTH = 100;

    String message() default "List size is too large.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int length() default DEFAULT_LENGTH;

    boolean nullAllowed() default false;

    boolean emptyListAllowed() default false;

    boolean nullElementsAllowed() default false;

    Response.Status status() default Response.Status.BAD_REQUEST;
}
