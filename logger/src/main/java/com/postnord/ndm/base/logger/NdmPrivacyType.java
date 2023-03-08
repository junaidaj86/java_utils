package com.postnord.ndm.base.logger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum NdmPrivacyType {
    MSISDN("priv1"),
    IMSI("priv2"),
    SIP_URI("priv3"),
    LOCATION_LAC_OR_CELL_ID("priv4,priv5"),
    IMEI("priv6"),
    LOCATION_OTHER("priv7"),
    IP_ADDRESS("priv8"),
    FIRST_NAME("priv9"),
    LAST_NAME("priv10"),
    USERNAME("priv11"),
    EMAIL("priv12"),
    PASSWORD("priv13"),
    DATE_OF_BIRTH("priv14"),
    COUNTRY("priv15"),
    STATE_OR_POSTAL_CODE("priv16"),
    ADDRESS("priv17"),
    SOCIAL_SECURITY_NUMBER("priv18"),
    CALL_HISTORY("priv19"),
    METADATA_USER_ACTIVITY("priv20"),
    BROWSING_OR_CONNECTION_HISTORY("priv21"),
    SUBSCRIBED_SERVICES("priv22"),
    CREDIT_CARD_NUMBER("priv23"),
    TRANSACTIONAL_INFORMATION("priv24"),
    NOT_SET("");

    final List<String> tags;

    NdmPrivacyType(final String... tags) {
        this.tags = Arrays.stream(tags).filter(s -> !s.isEmpty()).collect(Collectors.toList());
    }

    public String wrap(final Object value) {
        if (value == null) {
            return null;
        }
        final StringBuilder stringBuilder = new StringBuilder();
        tags.forEach(tag -> stringBuilder.append('[').append(tag).append(']'));
        stringBuilder.append(value);
        tags.forEach(tag -> stringBuilder.append("[/").append(tag).append(']'));
        return stringBuilder.toString();
    }
}