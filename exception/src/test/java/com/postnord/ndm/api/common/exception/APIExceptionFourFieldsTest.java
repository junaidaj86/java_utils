package com.postnord.ndm.api.common.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URI;
import java.util.stream.Stream;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class APIExceptionFourFieldsTest extends APIExceptionUtils {
    static final String MESSAGE = "something went wrong";

    @SuppressWarnings("PMD.UnusedPrivateMethod") //used as a parameterized source to feed our test case
    //whenCreateAPIExceptionWithoutAllRequiredFieldsSetThenAPIExceptionCannotBeBuilt()
    private static Stream<TestData> getTestData() {
        return Stream.of(
                new TestData("type", null, BAD_REQUEST.getReasonPhrase(), BAD_REQUEST.getStatusCode(), MESSAGE),
                new TestData("title", mock(URI.class), null, BAD_REQUEST.getStatusCode(), MESSAGE),
                new TestData("status", mock(URI.class), BAD_REQUEST.getReasonPhrase(), 0, MESSAGE),
                new TestData("detail", mock(URI.class), BAD_REQUEST.getReasonPhrase(), BAD_REQUEST.getStatusCode(), null)
        );
    }

    @Test
    void whenCreateAPIExceptionWithFourFieldsSetThenAPIExceptionCanBeBuilt() {

        final APIException eut = new APIException(mock(URI.class), BAD_REQUEST.getReasonPhrase(), BAD_REQUEST.getStatusCode(), MESSAGE);

        assertAll("API Exception fields",
                () -> assertNotNull(eut.getType(), "type was null"),
                () -> assertEquals(BAD_REQUEST.getReasonPhrase(), eut.getTitle(), "title not as expected"),
                () -> assertEquals(BAD_REQUEST.getStatusCode(), eut.getStatus(), "status not as expected"),
                () -> assertEquals("Bad Request: " + MESSAGE, eut.getMessage(), "message not as expected"));
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void whenCreateAPIExceptionWithoutAllRequiredFieldsSetThenAPIExceptionCannotBeBuilt(final TestData holder) {

        final Throwable exception = assertThrows(APIException.class, () -> new APIException(holder.getType(), holder.getTitle(),
                holder.getStatusCode(), holder.getDetail()));

        assertEquals("Bad Request: " + "Illegal value in field: '" + holder.getMessagePart() + "', reason: mandatory field '" +
                holder.getMessagePart() + "' must be specified", exception.getMessage(), "message not as expected");
    }
}
