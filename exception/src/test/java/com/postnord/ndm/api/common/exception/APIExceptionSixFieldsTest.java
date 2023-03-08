package com.postnord.ndm.api.common.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URI;
import java.util.Collections;
import java.util.stream.Stream;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

//should not matter in a unit test
class APIExceptionSixFieldsTest extends APIExceptionUtils {

    static final String MESSAGE = "something went wrong";

    @SuppressWarnings("PMD.UnusedPrivateMethod") //used as a parameterized source to feed our test case
    //whenCreateAPIExceptionWithoutRequiredFieldsSetThenAPIExceptionCannotBeBuilt()
    private static Stream<TestData> getTestData() {
        return Stream.of(
                new TestData("instance", mock(URI.class), BAD_REQUEST.getReasonPhrase(), BAD_REQUEST.getStatusCode(), MESSAGE, null, Collections.emptyMap()),
                new TestData("type", null, BAD_REQUEST.getReasonPhrase(), BAD_REQUEST.getStatusCode(), MESSAGE, mock(URI.class), Collections.emptyMap()),
                new TestData("title", mock(URI.class), null, BAD_REQUEST.getStatusCode(), MESSAGE, mock(URI.class), Collections.emptyMap()),
                new TestData("status", mock(URI.class), BAD_REQUEST.getReasonPhrase(), 0, MESSAGE, mock(URI.class), Collections.emptyMap()),
                new TestData("detail", mock(URI.class), BAD_REQUEST.getReasonPhrase(), BAD_REQUEST.getStatusCode(), null, mock(URI.class), Collections.emptyMap()),
                new TestData("parameters", mock(URI.class), BAD_REQUEST.getReasonPhrase(), BAD_REQUEST.getStatusCode(), MESSAGE, mock(URI.class), null)
        );
    }

    @Test
    void whenCreateAPIExceptionWithSixFieldsSetThenAPIExceptionCanBeBuilt() {

        final APIException eut = new APIException(mock(URI.class), "Bad Request", BAD_REQUEST.getStatusCode(), MESSAGE, mock(URI.class), Collections.emptyMap());

        assertAll("API Exception fields",
                () -> assertNotNull(eut.getType(), "type is null"),
                () -> assertEquals(BAD_REQUEST.getReasonPhrase(), eut.getTitle(), "title not as expected"),
                () -> assertEquals(BAD_REQUEST.getStatusCode(), eut.getStatus(), "status not as expected"),
                () -> assertEquals("Bad Request: " + MESSAGE, eut.getMessage(), "message not as expected"),
                () -> assertNotNull(eut.getInstance(), "instance is null"),
                () -> assertNotNull(eut.getParameters(), "parameters is null"));
    }

    @ParameterizedTest
    @MethodSource("getTestData")
    void whenCreateAPIExceptionWithoutRequiredFieldsSetThenAPIExceptionCannotBeBuilt(final TestData holder) {

        final Throwable exception = assertThrows(APIException.class, () -> new APIException(holder.getType(), holder.getTitle(),
                holder.getStatusCode(), holder.getDetail(), holder.getInstance(), holder.getParameters()));

        assertEquals("Bad Request: " + "Illegal value in field: '" + holder.getMessagePart() +
                        "', reason: mandatory field '" + holder.getMessagePart() + "' must be specified", exception.getMessage(),
                "message not as expected");
    }
}
