package com.postnord.ndm.api.common.validation.constraints;

import com.postnord.ndm.api.common.validation.validator.NdmCollectionStringLengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.ws.rs.core.Response;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER, TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NdmCollectionStringLengthValidator.class)
@Documented
public @interface NdmCollectionStringLength {

    int DEFAULT_LENGTH = 100;

    String message() default "Collection contains too large string.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int length() default DEFAULT_LENGTH;

    boolean nullAllowed() default false;

    Response.Status status() default Response.Status.BAD_REQUEST;

}
