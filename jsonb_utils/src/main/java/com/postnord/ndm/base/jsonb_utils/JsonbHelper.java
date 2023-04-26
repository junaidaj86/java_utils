package com.postnord.ndm.base.jsonb_utils;

import jakarta.json.JsonMergePatch;
import jakarta.json.JsonObject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.config.PropertyNamingStrategy;

public final class JsonbHelper {
    private static final Jsonb JSONB = createJsonb();

    public static <T> T mergePatch(final JsonMergePatch mergePatch, final T targetBean, final Class<T> beanClass) {
        final var target = JSONB.fromJson(JSONB.toJson(targetBean), JsonObject.class);
        final var patched = mergePatch.apply(target);/**/
        return JSONB.fromJson(JSONB.toJson(patched), beanClass);
    }

    public static Jsonb createJsonb() {
        return JsonbBuilder.create(createJsonbConfig());
    }

    public static JsonbConfig createJsonbConfig() {
        return initJsonbConfig(new JsonbConfig());
    }

    static JsonbConfig initJsonbConfig(final JsonbConfig jsonbConfig) {
        return jsonbConfig
                .withPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE_WITH_UNDERSCORES)
                .withAdapters(new JsonMergePatchAdapter(), new LocalDateAdapter(), new InstantAdapter(), new OffsetDateTimeAdapter());
    }

    private JsonbHelper() {
    }
}
