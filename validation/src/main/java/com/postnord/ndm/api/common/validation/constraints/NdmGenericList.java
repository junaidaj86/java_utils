package com.postnord.ndm.api.common.validation.constraints;

import com.postnord.ndm.api.common.validation.validator.NdmGenericListValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.ws.rs.core.Response;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @deprecated Replaced by NdmGenericCollection
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER, TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NdmGenericListValidator.class)
@Documented
@Deprecated
public @interface NdmGenericList {

    String message() default "List expected.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean nullAllowed() default false;

    boolean emptyListAllowed() default false;

    boolean nullElementsAllowed() default false;

    Response.Status status() default Response.Status.BAD_REQUEST;
}
