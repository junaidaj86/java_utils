package com.postnord.ndm.api.common.mapper;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

@ConfigMapping(prefix = "com.postnord.ndm.base.exception")
public interface MapperConfiguration {
    @WithDefault("UNSET")
    String logCategory();
}
