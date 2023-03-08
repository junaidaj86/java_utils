package com.postnord.ndm.api.common.validation.constraints;

import com.postnord.ndm.api.common.validation.validator.NdmUuId4StringValidator;

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
@Constraint(validatedBy = NdmUuId4StringValidator.class)
@Documented
public @interface NdmUuId4String {

    String message() default "String is not in UUID4 format.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean nullAllowed() default false;

    Response.Status status() default Response.Status.BAD_REQUEST;
}
