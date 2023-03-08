package com.postnord.ndm.base.logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.stream.Collectors;

class NdmPrivacyTypeTest {

    @ParameterizedTest
    @EnumSource(NdmPrivacyType.class)
    void wrapNullValueShouldReturnNull(final NdmPrivacyType privacyType) {
        Assertions.assertNull(privacyType.wrap(null));
    }

    @ParameterizedTest
    @EnumSource(NdmPrivacyType.class)
    void wrapStringValueShouldReturnWrappedValue(final NdmPrivacyType privacyType) {
        final String value = "value";
        final String wrappedValue = privacyType.wrap(value);

        Assertions.assertEquals(wrapValue(privacyType, value), wrappedValue);
    }

    @Test
    void wrapOtherValueShouldReturnWrappedValue() {
        final Long value = Math.round(Math.random());
        final String wrappedValue = NdmPrivacyType.IMSI.wrap(value);

        Assertions.assertEquals(wrapValue(NdmPrivacyType.IMSI, value.toString()), wrappedValue);
    }

    private static String wrapValue(final NdmPrivacyType privacyType, final String value) {
        final String expectedHeadValue = privacyType.tags.stream().map(tag -> "[" + tag + "]").collect(Collectors.joining(","));
        final String expectedTailValue = privacyType.tags.stream().map(tag -> "[/" + tag + "]").collect(Collectors.joining(","));

        return expectedHeadValue + value + expectedTailValue;
    }
}
