package com.postnord.ndm.api.common.validation.constraints;

import com.postnord.ndm.api.common.validation.validator.NdmCollectionSizeValidator;

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
@Constraint(validatedBy = NdmCollectionSizeValidator.class)
@Documented
public @interface NdmCollectionSize {

    int DEFAULT_SIZE = 10;

    String message() default "Collection size is too large.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int size() default DEFAULT_SIZE;

    boolean nullAllowed() default false;

    Response.Status status() default Response.Status.BAD_REQUEST;
}
