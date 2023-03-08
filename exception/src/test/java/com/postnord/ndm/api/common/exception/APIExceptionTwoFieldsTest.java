package com.postnord.ndm.api.common.exception;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class APIExceptionTwoFieldsTest {

    static final String MESSAGE = "something went wrong";

    @Test
    void whenCreateAPIExceptionWithTwoFieldsSetThenAPIExceptionCanBeBuilt() {

        final APIException eut = new APIException(mock(URI.class), MESSAGE);

        assertAll("API Exception fields",
                () -> assertNotNull(eut.getType(), "type was null"),
                () -> assertEquals(MESSAGE + ": UNSET", eut.getMessage(), "message not as expected"));
    }

    @Test
    void whenCreateAPIExceptionWithoutURIFieldsSetThenAPIExceptionCannotBeBuilt() {

        final Throwable exception = assertThrows(APIException.class, () -> {
            new APIException(null, MESSAGE);
        });

        assertEquals("Bad Request: " + "Illegal value in field: 'type', reason: mandatory field 'type' must be specified",
                exception.getMessage(), "message not as expected");
    }

    @Test
    void whenCreateAPIExceptionWithoutDetailFieldsSetThenAPIExceptionCannotBeBuilt() {

        final Throwable exception = assertThrows(APIException.class, () -> {
            new APIException(mock(URI.class), null);
        });

        assertEquals("Bad Request: " + "Illegal value in field: 'title', reason: mandatory field 'title' must be specified",
                exception.getMessage(), "message not as expected");
    }
}
