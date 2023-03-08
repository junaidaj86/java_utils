package com.postnord.ndm.api.common.validation.constraints;

import com.postnord.ndm.api.common.validation.validator.NdmArrayLengthValidator;

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
@Constraint(validatedBy = NdmArrayLengthValidator.class)
@Documented
public @interface NdmArrayLength {

    int DEFAULT_MAX = 100;
    int DEFAULT_MIN = 0;

    String message() default "Array length is out of bounds.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default DEFAULT_MIN;

    int max() default DEFAULT_MAX;

    boolean nullAllowed() default false;

    Response.Status status() default Response.Status.BAD_REQUEST;
}
