package com.postnord.ndm.base.jsonb_utils;

import javax.json.JsonMergePatch;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.config.PropertyNamingStrategy;

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
