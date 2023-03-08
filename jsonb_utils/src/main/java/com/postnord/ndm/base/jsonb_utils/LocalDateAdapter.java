package com.postnord.ndm.base.jsonb_utils;


import com.postnord.ndm.api.common.exception.APIException;

import javax.json.bind.adapter.JsonbAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

public class LocalDateAdapter implements JsonbAdapter<LocalDate, String> {
    private static final String SUPPORTED_DATE_FORMATS = "Supported date formats include the ISO-8601 extended date format.";

    @Override
    public String adaptToJson(final LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return localDate.format(DateTimeFormatter.ISO_DATE);
    }

    @Override
    @SuppressWarnings("PMD.PreserveStackTrace")
    public LocalDate adaptFromJson(final String json) {
        if (json == null) {
            return null;
        }
        try {
            return LocalDate.parse(json, DateTimeFormatter.ISO_DATE);
        } catch (DateTimeParseException e) {
            throw new APIException(BAD_REQUEST.getReasonPhrase(), BAD_REQUEST.getStatusCode(),
                    "Invalid date format. " + SUPPORTED_DATE_FORMATS);
        }
    }
}
