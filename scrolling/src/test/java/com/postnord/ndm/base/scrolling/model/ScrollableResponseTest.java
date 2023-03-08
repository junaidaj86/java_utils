package com.postnord.ndm.base.scrolling.model;

import com.postnord.ndm.base.common_utils.utils.InstantHelper;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.Period;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ScrollableResponseTest {
    private static final int DEFAULT_STATUS = 200;
    private static final String EXPECTED_MEDIA_TYPE = MediaType.APPLICATION_JSON;
    private static final String DEFAULT_RAW_PARAMS = "status==ACTIVE&sort=+eventAt";
    private static final String ASSERT_HEADING = "Scrollable was not built with correct properties";
    private static final Instant DATE = InstantHelper.getInstantAndStripNano().minus(Period.ofDays(1));

    final List<String> largeResults = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
    final List<String> smallResult = Arrays.asList("1", "2");

    @Test
    void whenOnSecondToLastPageThenNextIsPresentAndAccurate() {

        try (Response response = ScrollableResponse.builder()
                .scrollable(Scrollable.builder()
                        .items(smallResult)
                        .page(5).limit(2)
                        .rawQueryParams(DEFAULT_RAW_PARAMS)
                        .totalCount(12)
                        .createdAt(DATE)
                        .status(DEFAULT_STATUS)
                        .build())
                .build()) {

            final Scrollable scrollableParsedFromResponse = response.readEntity(Scrollable.class);

            assertAll(ASSERT_HEADING,
                    () -> assertNotNull(response),
                    () -> assertNotNull(scrollableParsedFromResponse),
                    () -> assertEquals(DEFAULT_STATUS, response.getStatus()),
                    () -> assertEquals(EXPECTED_MEDIA_TYPE, response.getMediaType().toString()),
                    () -> assertEquals("?" + DEFAULT_RAW_PARAMS + "&cursor=6&limit=2&created_at=" + DATE
                            , scrollableParsedFromResponse.getNextLinkDecoded()),
                    () -> assertEquals(smallResult.toString(), scrollableParsedFromResponse.getItems().toString())
            );
        }
    }

    @Test
    void whenOnLastPageThenNextIsEmpty() {

        try (Response response = ScrollableResponse.builder()
                .scrollable(Scrollable.builder()
                        .items(largeResults)
                        .page(6).limit(2).createdAt(DATE)
                        .rawQueryParams(DEFAULT_RAW_PARAMS)
                        .totalCount(12)
                        .status(DEFAULT_STATUS)
                        .build())
                .build()) {

            final Scrollable scrollableParsedFromResponse = response.readEntity(Scrollable.class);

            assertAll(ASSERT_HEADING,
                    () -> assertNotNull(response),
                    () -> assertNotNull(scrollableParsedFromResponse),
                    () -> assertEquals(DEFAULT_STATUS, response.getStatus()),
                    () -> assertEquals(EXPECTED_MEDIA_TYPE, response.getMediaType().toString()),
                    () -> assertNotNull(scrollableParsedFromResponse),
                    () -> assertNull(scrollableParsedFromResponse.getNextLinkEncoded())
            );
        }
    }

    @Test
    void whenOnSecondToLastPageUsingHasNextThenNextIsPresentAndAccurate() {

        try (Response response = ScrollableResponse.builder()
                .scrollable(Scrollable.builder()
                        .items(smallResult)
                        .page(5).limit(2).createdAt(DATE)
                        .rawQueryParams(DEFAULT_RAW_PARAMS)
                        .hasNextPage(true)
                        .status(DEFAULT_STATUS)
                        .build())
                .build()) {

            final Scrollable scrollableParsedFromResponse = response.readEntity(Scrollable.class);

            assertAll(ASSERT_HEADING,
                    () -> assertNotNull(response),
                    () -> assertNotNull(scrollableParsedFromResponse),
                    () -> assertEquals(DEFAULT_STATUS, response.getStatus()),
                    () -> assertEquals(EXPECTED_MEDIA_TYPE, response.getMediaType().toString()),
                    () -> assertNotNull(scrollableParsedFromResponse),
                    () -> assertEquals("?" + DEFAULT_RAW_PARAMS + "&cursor=6&limit=2&created_at=" + DATE, scrollableParsedFromResponse.getNextLinkDecoded())
            );
        }
    }
}
