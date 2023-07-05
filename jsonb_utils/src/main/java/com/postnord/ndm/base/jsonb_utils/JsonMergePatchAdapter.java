package com.postnord.ndm.base.jsonb_utils;

import jakarta.json.JsonValue;
import jakarta.json.bind.adapter.JsonbAdapter;
import org.eclipse.parsson.JsonMergePatchImpl;

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
        return new JsonMergePatchImpl(jsonValue, null);
    }
}
