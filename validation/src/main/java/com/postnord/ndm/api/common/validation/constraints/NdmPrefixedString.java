package com.postnord.ndm.api.common.validation.constraints;

import com.postnord.ndm.api.common.validation.validator.NdmPrefixedStringValidator;

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
@Constraint(validatedBy = NdmPrefixedStringValidator.class)
@Documented
public @interface NdmPrefixedString {

    String message() default "Value must be prefixed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String prefix() default "tel:";

    boolean nullAllowed() default false;

    Response.Status status() default Response.Status.NOT_FOUND;
}
