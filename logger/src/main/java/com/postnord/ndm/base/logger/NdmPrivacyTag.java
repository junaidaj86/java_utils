package com.postnord.ndm.base.logger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NdmPrivacyTag {
    NdmPrivacyType value() default NdmPrivacyType.NOT_SET;
}