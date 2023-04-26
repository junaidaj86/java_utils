package com.postnord.ndm.base.jsonb_utils;

import jakarta.inject.Singleton;
import jakarta.json.bind.JsonbConfig;

import io.quarkus.jsonb.JsonbConfigCustomizer;

@Singleton
public class JsonbCustomizer implements JsonbConfigCustomizer {

    @Override
    public void customize(final JsonbConfig jsonbConfig) {
        JsonbHelper.initJsonbConfig(jsonbConfig);
    }
}
