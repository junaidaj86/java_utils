package com.postnord.ndm.api.common.validation.constraints;

import com.postnord.ndm.api.common.validation.validator.NdmJsonMergePatchValidator;

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
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@SuppressWarnings("PMD.TooManyStaticImports")
@Target({METHOD, FIELD, ANNOTATION_TYPE, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = NdmJsonMergePatchValidator.class)
@Documented
public @interface NdmJsonMergePatch {

    String DEFAULT_MESSAGE = "Invalid patch.";

    String message() default DEFAULT_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    NdmField[] fields();

    boolean nullAllowed() default false;

    boolean emptyAllowed() default false;

    Response.Status status() default Response.Status.BAD_REQUEST;
}
