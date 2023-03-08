package com.postnord.ndm.api.common.exception;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class APIExceptionTest {

    @Test
    void whenCreateAPIExceptionWithNoFieldThenAPIExceptionCanBeBuilt() {
        final APIException eut = new APIException();
        assertNotNull(eut, "default construction failed");
    }

    @Test
    void whenCreateAPIExceptionWithURIFieldThenAPIExceptionCanBuilt() {
        final APIException eut = new APIException(mock(URI.class));
        assertNotNull(eut.getType(), "type was null");
    }
}
