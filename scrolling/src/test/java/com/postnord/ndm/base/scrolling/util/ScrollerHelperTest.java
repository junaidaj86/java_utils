package com.postnord.ndm.base.scrolling.util;

import com.postnord.ndm.api.common.exception.APIException;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("PMD.TooManyStaticImports")
class ScrollerHelperTest {

    private static final String QUERY_PARAMS = "?sort=+eventAt&cursor=1&limit=2&q=status==ACTIVE";
    private static final String QUERY_PARAMS_BASE64 = Base64.getEncoder().encodeToString(QUERY_PARAMS.getBytes(StandardCharsets.UTF_8));

    @Test
    void testWhenCursorIsNullThenPageNumberZeroShouldReturned() {
        assertEquals(0, ScrollerHelper.getPageNumber(null));
    }

    @Test
    void testWhenCursorIsNumericThenPageNumberShouldReturned() {
        assertEquals(1, ScrollerHelper.getPageNumber("1"));
    }

    @Test
    void testWhenCursorIsNotNumericThenAPIExceptionShouldBeThrown() {
        assertThrows(APIException.class, () -> ScrollerHelper.getPageNumber("a"));
    }

    @Test
    void testWhenNextLinkIsNullThenNullShouldReturned() {
        assertNull(ScrollerHelper.getNextLinkDecoded(null));
    }

    @Test
    void testWhenNextLinkIsBlankThenEmptyStringShouldReturned() {
        assertEquals("", ScrollerHelper.getNextLinkDecoded(" "));
    }

    @Test
    void testWhenNextLinkIsBase64ThenQueryParamsShouldReturned() {
        assertEquals(QUERY_PARAMS, ScrollerHelper.getNextLinkDecoded(QUERY_PARAMS_BASE64));
    }

    @Test
    void testWhenNextLinkIsInvalidThenAPIExceptionShouldBeThrown() {
        assertThrows(APIException.class, () -> ScrollerHelper.getNextLinkDecoded("1"));
    }

    @Test
    void testWhenNextLinkIsNullThenEmptyMapShouldReturned() {
        assertTrue(ScrollerHelper.getNextParamsMapDecoded(null).isEmpty());
    }

    @Test
    void testWhenNextLinkIsBlankThenEmptyMapShouldReturned() {
        assertTrue(ScrollerHelper.getNextParamsMapDecoded(" ").isEmpty());
    }

    @Test
    void testWhenNextLinkIsInvalidThenEmptyMapShouldReturned() {
        assertTrue(ScrollerHelper.getNextParamsMapDecoded(Base64.getEncoder().encodeToString("?foo".getBytes())).isEmpty());
    }

    @Test
    void testWhenNextLinkIsBase64ThenParamsMapShouldReturned() {
        final Map<String, String> params = ScrollerHelper.getNextParamsMapDecoded(QUERY_PARAMS_BASE64);
        assertEquals("+eventAt", params.get(ResultUtils.SORT));
        assertEquals("1", params.get(ResultUtils.CURSOR));
        assertEquals("status==ACTIVE", params.get(ResultUtils.Q));
        assertEquals("2", params.get(ResultUtils.LIMIT));
    }

    @Test
    void testWhenNextLinkParamValueIsNotSetThenParamsMapShouldReturned() {
        final Map<String, String> params = ScrollerHelper.getNextParamsMapDecoded(Base64.getMimeEncoder().encodeToString("?foo=".getBytes()));
        assertEquals("", params.get("foo"));
    }

    @Test
    void testWhenNextLinkIsNotBase64ThenAPIExceptionShouldBeThrown() {
        assertThrows(APIException.class, () -> ScrollerHelper.getNextParamsMapDecoded("1"));
    }

    @Test
    void whenCursorIsNullThenFalseShouldBeReturned() {
        assertFalse(ScrollerHelper.isEncodedNextLink(null));
    }

    @Test
    void whenCursorIsNotMultipleOfFourThenFalseShouldBeReturned() {
        assertFalse(ScrollerHelper.isEncodedNextLink("a"));
    }

    @Test
    void whenCursorIsNumericThenFalseShouldBeReturned() {
        assertFalse(ScrollerHelper.isEncodedNextLink("1234"));
    }

    @Test
    void whenCursorIsBase64ThenTrueShouldBeReturned() {
        assertTrue(ScrollerHelper.isEncodedNextLink(QUERY_PARAMS_BASE64));
    }
}
