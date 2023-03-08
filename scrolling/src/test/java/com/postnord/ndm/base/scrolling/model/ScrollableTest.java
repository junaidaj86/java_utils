package com.postnord.ndm.base.scrolling.model;

import com.postnord.ndm.base.common_utils.utils.InstantHelper;
import com.postnord.ndm.base.scrolling.util.ScrollerHelper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.Period;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScrollableTest {

    private static final int DEFAULT_STATUS = 200;
    private static final String DEFAULT_RAW_PARAMS = "status==ACTIVE&sort=+eventAt";
    private static final Instant DATE = InstantHelper.getInstantAndStripNano().minus(Period.ofDays(2));
    private List<String> largeResults;

    @BeforeEach
    void setUp() {
        largeResults = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
    }

    @Test
    void whenOnSecondToLastPageNextIsPresentAndAccurate() {
        final String pageNumber = "6";

        final Scrollable scroller = Scrollable.builder()
                .items(largeResults)
                .page(5)
                .limit(2)
                .createdAt(DATE)
                .rawQueryParams(DEFAULT_RAW_PARAMS)
                .totalCount(12)
                .status(DEFAULT_STATUS)
                .build();

        final String queryParams = "?status==ACTIVE&sort=+eventAt&cursor=" + pageNumber + "&limit=2" + "&created_at=" + DATE;

        assertAll("Check attributes",
                () -> assertEquals(queryParams, scroller.getNextLinkDecoded())
        );
        assertEquals(pageNumber,
                ScrollerHelper.getNextParamsMapDecoded(scroller.getNextLinkEncoded())
                        .get("cursor"));

    }

    @Test
    void whenScrollableIsBuiltNoConfigurationThenIllegalArgumentExceptionIsThrown() {

        assertThrows(IllegalArgumentException.class, () -> Scrollable.builder().build());
    }

    @Test
    void whenScrollableIsBuiltMissingPageThenIllegalArgumentExceptionIsThrown() {

        assertThrows(IllegalArgumentException.class, () -> Scrollable.builder()
                .items(largeResults)
                .limit(2)
                .rawQueryParams(DEFAULT_RAW_PARAMS)
                .totalCount(12)
                .status(DEFAULT_STATUS)
                .build());
    }

    @Test
    void whenScrollableIsBuiltMissingLimitThenIllegalArgumentExceptionIsThrown() {

        assertThrows(IllegalArgumentException.class, () -> Scrollable.builder()
                .items(largeResults)
                .page(2)
                .rawQueryParams(DEFAULT_RAW_PARAMS)
                .totalCount(12)
                .status(DEFAULT_STATUS)
                .build());
    }

    @Test
    void whenScrollableIsBuiltMissingRawQueryParamsThenIllegalArgumentExceptionIsThrown() {

        assertThrows(IllegalArgumentException.class, () -> Scrollable.builder()
                .items(largeResults)
                .page(2)
                .limit(2)
                .totalCount(12)
                .status(DEFAULT_STATUS)
                .build());
    }

    @Test
    void whenScrollableIsBuiltMissingItemsThenIllegalArgumentExceptionIsThrown() {

        assertThrows(IllegalArgumentException.class, () -> Scrollable.builder()
                .page(2)
                .limit(2)
                .rawQueryParams(DEFAULT_RAW_PARAMS)
                .totalCount(12)
                .status(DEFAULT_STATUS)
                .build());
    }

    @Test
    void whenScrollableIsBuiltMissingNextInformationThenIllegalArgumentExceptionIsThrown() {

        assertThrows(IllegalArgumentException.class, () -> Scrollable.builder()
                .items(largeResults)
                .page(2)
                .limit(2)
                .rawQueryParams(DEFAULT_RAW_PARAMS)
                .status(DEFAULT_STATUS)
                .build());
    }
}
