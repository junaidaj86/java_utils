package com.postnord.ndm.api.common.security.util;

import java.time.ZoneId;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ConstantsHelper {

    public static final String BEARER_RESERVED_WORD = "Bearer";
    public static final ZoneId UTC_ZONE = ZoneId.of("UTC");
}
