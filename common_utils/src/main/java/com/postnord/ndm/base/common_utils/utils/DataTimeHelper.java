package com.postnord.ndm.base.common_utils.utils;

import org.eclipse.microprofile.config.ConfigProvider;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

final class DataTimeHelper {

    static final DateTimeFormatter DATE_TIME_OUTPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    static final ZoneOffset DEFAULT_OFFSET = getDefaultOffset();

    static final String SUPPORTED_TIMESTAMP_FORMATS =
            "Supported timestamp formats include the ISO-8601 extended local or offset date-time format," +
                    " as well as the extended non-ISO form specifying the time-zone.";

    static ZoneOffset getDefaultOffset() {
        return ZoneOffset.of(
                ConfigProvider.getConfig()
                        .getOptionalValue("com.ericsson.iota.base.common_utils.offset.default", String.class)
                        .orElse("Z")
        );
    }

    private DataTimeHelper() {
    }
}
