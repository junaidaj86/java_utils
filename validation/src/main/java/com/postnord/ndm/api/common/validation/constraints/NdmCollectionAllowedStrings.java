package com.postnord.ndm.api.common.validation.constraints;

import com.postnord.ndm.api.common.validation.validator.NdmCollectionAllowedStringsValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.ws.rs.core.Response;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER, TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NdmCollectionAllowedStringsValidator.class)
@Documented
@SuppressWarnings("PMD.TooManyStaticImports")
public @interface NdmCollectionAllowedStrings {

    String message() default "Collection value not allowed.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] values();

    boolean nullAllowed() default false;

    boolean emptyAllowed() default false;

    Response.Status status() default Response.Status.BAD_REQUEST;
}
