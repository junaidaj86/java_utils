package com.postnord.ndm.api.common.validation.constraints;

import com.postnord.ndm.api.common.validation.validator.NdmTagListValidator;

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
@Constraint(validatedBy = NdmTagListValidator.class)
@Documented
public @interface NdmTagList {

    String message() default "Tag list of invalid size, tag list item of invalid size or tag list item not in valid format.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int maxListLength() default 100;

    int maxListItemLength() default 100;

    String listItemRegex() default "^\\w+:\\w+$";

    boolean nullAllowed() default false;

    Response.Status status() default Response.Status.BAD_REQUEST;
}
