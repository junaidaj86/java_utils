package com.postnord.ndm.base.jsonb_utils;


import com.postnord.ndm.base.common_utils.utils.InstantHelper;

import javax.json.bind.adapter.JsonbAdapter;
import java.time.Instant;

public class InstantAdapter implements JsonbAdapter<Instant, String> {

    @Override
    public String adaptToJson(final Instant instant) {
        return InstantHelper.getStringFromInstant(instant);
    }

    @Override
    public Instant adaptFromJson(final String json) {
        return InstantHelper.getInstantFromString(json);
    }

}
