package com.postnord.ndm.base.jsonb_utils;


import com.postnord.ndm.base.common_utils.utils.OffsetDateTimeHelper;

import jakarta.json.bind.adapter.JsonbAdapter;
import java.time.OffsetDateTime;

public class OffsetDateTimeAdapter implements JsonbAdapter<OffsetDateTime, String> {

    @Override
    public String adaptToJson(final OffsetDateTime offsetDateTime) {
        return OffsetDateTimeHelper.getStringFromOffsetDateTime(offsetDateTime);
    }

    @Override
    public OffsetDateTime adaptFromJson(final String json) {
        return OffsetDateTimeHelper.getOffsetDateTimeFromString(json);
    }
}
