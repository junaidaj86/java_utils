package com.postnord.ndm.api.common.validation.constraints;

import com.postnord.ndm.api.common.validation.validator.NdmUniqueListValidator;

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
@Constraint(validatedBy = NdmUniqueListValidator.class)
@Documented
public @interface NdmUniqueList {

    String message() default "List contains non unique values.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean nullAllowed() default false;

    Response.Status status() default Response.Status.BAD_REQUEST;
}
