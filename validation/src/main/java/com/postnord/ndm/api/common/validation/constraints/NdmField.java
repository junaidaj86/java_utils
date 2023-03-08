package com.postnord.ndm.api.common.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface NdmField {
    String name() default "";

    boolean isArray() default false;

    String[] path() default {};
}
