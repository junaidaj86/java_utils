package com.postnord.ndm.base.jsonb_utils;

import org.glassfish.json.JsonMergePatchImpl;

import javax.json.JsonValue;
import javax.json.bind.adapter.JsonbAdapter;

public class JsonMergePatchAdapter implements JsonbAdapter<JsonMergePatchImpl, JsonValue> {
    @Override
    public JsonValue adaptToJson(final JsonMergePatchImpl jsonMergePatch) {
        if (jsonMergePatch == null) {
            return null;
        }
        return jsonMergePatch.toJsonValue();
    }

    @Override
    public JsonMergePatchImpl adaptFromJson(final JsonValue jsonValue) {
        if (jsonValue == null) {
            return null;
        }
        return new JsonMergePatchImpl(jsonValue);
    }
}
