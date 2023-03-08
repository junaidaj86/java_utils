package com.postnord.ndm.api.common.validation.constraints;

import com.postnord.ndm.api.common.validation.validator.NdmGenericCollectionValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.ws.rs.core.Response;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER, TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NdmGenericCollectionValidator.class)
@Documented
@SuppressWarnings("PMD.TooManyStaticImports")
public @interface NdmGenericCollection {

    String message() default "Collection expected.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean nullAllowed() default false;

    boolean emptyAllowed() default false;

    boolean nullElementsAllowed() default false;

    Response.Status status() default Response.Status.BAD_REQUEST;
}
