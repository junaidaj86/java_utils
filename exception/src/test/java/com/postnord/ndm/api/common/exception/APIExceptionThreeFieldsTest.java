package com.postnord.ndm.api.common.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URI;
import java.util.stream.Stream;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

//should not matter in a unit test
class APIExceptionThreeFieldsTest {

    static final String MESSAGE = "something went wrong";

    @SuppressWarnings("PMD.UnusedPrivateMethod") //// used as a parameterized source to feed our tests
    //whenCreateAPIExceptionWithoutAllRequiredFieldsSetThenAPIExceptionCannotBeBuilt()
    private static Stream<APIExceptionUtils.TestData> getTestData() {
        return Stream.of(
                new APIExceptionUtils.TestData("title", null, BAD_REQUEST.getStatusCode(), MESSAGE),
                new APIExceptionUtils.TestData("status", BAD_REQUEST.getReasonPhrase(), 0, MESSAGE),
                new APIExceptionUtils.TestData("detail", BAD_REQUEST.getReasonPhrase(), BAD_REQUEST.getStatusCode(), null)
        );
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod") //used as a parameterized source to feed our tests
    //whenCreateAPIExceptionWithoutAllRequiredFieldsSet2ThenAPIExceptionCannotBeBuilt()
    private static Stream<APIExceptionUtils.TestData> getTestData2() {
        return Stream.of(
                new APIExceptionUtils.TestData("type", null, BAD_REQUEST.getReasonPhrase(), BAD_REQUEST.getStatusCode()),
                new APIExceptionUtils.TestData("title", mock(URI.class), null, BAD_REQUEST.getStatusCode()),
                new APIExceptionUtils.TestData("status", mock(URI.class), BAD_REQUEST.getReasonPhrase(), 0)
        );
    }

    @Test
    void whenCreateAPIExceptionWithThreeFieldsSetThenAPIExceptionCanBeBuilt() {

        final APIException eut = new APIException(BAD_REQUEST.getReasonPhrase(), BAD_REQUEST.getStatusCode(), MESSAGE);

        assertAll("API Exception fields",
                () -> assertEquals("Bad Request: " + MESSAGE, eut.getMessage(), "message not as expected"),
                () -> assertEquals(BAD_REQUEST.getStatusCode(), eut.getStatus(), "status not as expected"),
                () -> assertEquals(BAD_REQUEST.getReasonPhrase(), eut.getTitle(), "title not as expected"));
    }

    @Test
    void whenCreateAPIExceptionWithThreeFieldsSet2ThenAPIExceptionCanBeBuilt() {

        final APIException eut = new APIException(mock(URI.class), BAD_REQUEST.getReasonPhrase(),
                BAD_REQUEST.getStatusCode());

        assertAll("API Exception fields",
                () -> assertNotNull(eut.getType(), "type was null"),
                () -> assertEquals(BAD_REQUEST.getReasonPhrase(), eut.getTitle(), "title not as expected"),
                () -> assertEquals(BAD_REQUEST.getStatusCode(), eut.getStatus(), "status not as expected"));
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void whenCreateAPIExceptionWithoutAllRequiredFieldsSetThenAPIExceptionCannotBeBuilt(final APIExceptionUtils.TestData holder) {

        final Throwable exception = assertThrows(APIException.class, () ->
                new APIException(holder.getTitle(), holder.getStatusCode(), holder.getDetail()));

        assertEquals("Bad Request: " + "Illegal value in field: '" + holder.getMessagePart() + "', reason: mandatory field '"
                + holder.getMessagePart() + "' must be specified", exception.getMessage(), "message not as expected");
    }

    @ParameterizedTest
    @MethodSource("getTestData2")
    void whenCreateAPIExceptionWithoutAllRequiredFieldsSet2ThenAPIExceptionCannotBeBuilt(final APIExceptionUtils.TestData holder) {

        final Throwable exception = assertThrows(APIException.class, () -> new APIException(holder.getType(), holder.getTitle(),
                holder.getStatusCode()));
        assertEquals("Bad Request: " + "Illegal value in field: '" + holder.getMessagePart() +
                        "', reason: mandatory field '" + holder.getMessagePart() + "' must be specified", exception.getMessage(),
                "message not as expected");
    }
}
