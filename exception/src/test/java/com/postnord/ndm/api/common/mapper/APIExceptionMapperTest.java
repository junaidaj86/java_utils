package com.postnord.ndm.api.common.mapper;

import com.postnord.ndm.api.common.exception.APIException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import io.quarkus.test.junit.QuarkusTest;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@SuppressWarnings("PMD.TooManyStaticImports")
class APIExceptionMapperTest {

    public static final String STATUS_NOT_AS_EXPECTED = "status not as expected";
    public static final String MESSAGE_NOT_AS_EXPECTED = "message not as expected";
    private static final String MESSAGE = "don't panic just testing exception";
    private APIException apiException;

    @Inject
    APIExceptionMapper apiExceptionMapper;

    @BeforeEach
    public void setUp() {
        apiException = new APIException("Bad Request", BAD_REQUEST.getStatusCode(), MESSAGE);
    }

    @Test
    void whenMapperTranslatesFromAPIExceptionThenResponseContainsCorrectErrorCode() {

        try (Response response = apiExceptionMapper.toResponse(apiException)) {

            assertNotNull(response, "response is null");
            assertTrue(response.getEntity().toString().contains(String.valueOf(BAD_REQUEST)),
                    "status code in entity not as expected");
        }
    }

    @Test
    void whenMapperTranslatesFromAPIExceptionThenResponseContainsCorrectMessage() {

        try (Response response = apiExceptionMapper.toResponse(apiException)) {

            assertNotNull(response, "response is null");
            assertTrue(response.getEntity().toString().contains(BAD_REQUEST.getReasonPhrase()),
                    "reason phrase in entity not as expected");
        }
    }

    @Test
    void whenMapperTranslatesFromAPIExceptionThenResponseContainsCorrectParameters() throws URISyntaxException {
        final Map<String, Object> parametermap = Map.of("parameterKey", List.of("paramValue"));
        final APIException apiException = new APIException(new URI("endpoint"), BAD_REQUEST.getReasonPhrase(),
                BAD_REQUEST.getStatusCode(), BAD_REQUEST.getReasonPhrase(), new URI("endpoint"), parametermap);

        try (Response response = apiExceptionMapper.toResponse(apiException)) {
            assertNotNull(response, "response is null");
            assertTrue(response.getEntity().toString().contains(BAD_REQUEST.getReasonPhrase()),
                    "reason phrase in entity not as expected");
            assertTrue(response.getEntity().toString().contains("parameterKey"), "parameters in entity not as expected");
        }
    }

    @Test
    void whenTitleIsEmptyThenThrowException() {

        final APIException apiException = assertThrows(APIException.class, () -> {
            new APIException(new URI("uri"), null);
        });

        assertEquals("Bad Request: Illegal value in field: 'title', reason: mandatory field 'title' must be specified",
                apiException.getMessage(), MESSAGE_NOT_AS_EXPECTED);

        assertEquals(BAD_REQUEST.getStatusCode(), apiException.getStatus(), STATUS_NOT_AS_EXPECTED);
    }

    @Test
    void whenTypeIsEmptyThenThrowException() {

        final APIException apiException = assertThrows(APIException.class, () -> {
            new APIException(null, "title");
        });

        assertEquals("Bad Request: Illegal value in field: 'type', reason: mandatory field 'type' must be specified",
                apiException.getMessage(), MESSAGE_NOT_AS_EXPECTED);

        assertEquals(BAD_REQUEST.getStatusCode(), apiException.getStatus(), STATUS_NOT_AS_EXPECTED);
    }

    @Test
    void whenTypeIsEmptyWithStatusThenThrowException() {

        final APIException apiException = assertThrows(APIException.class, () -> {
            new APIException(null, "title", 1);
        });

        assertEquals("Bad Request: Illegal value in field: 'type', reason: mandatory field 'type' must be specified",
                apiException.getMessage(), MESSAGE_NOT_AS_EXPECTED);

        assertEquals(BAD_REQUEST.getStatusCode(), apiException.getStatus(), STATUS_NOT_AS_EXPECTED);
    }

    @Test
    void whenTitleIsEmptyWithStatusThenThrowException() {

        final APIException apiException = assertThrows(APIException.class, () -> {
            new APIException(new URI("uri"), null, 1);
        });

        assertEquals("Bad Request: Illegal value in field: 'title', reason: mandatory field 'title' must be specified",
                apiException.getMessage(), MESSAGE_NOT_AS_EXPECTED);

        assertEquals(BAD_REQUEST.getStatusCode(), apiException.getStatus(), STATUS_NOT_AS_EXPECTED);
    }

    @Test
    void whenStatusIsZeroThenThrowException() {

        final APIException apiException = assertThrows(APIException.class, () -> {
            new APIException(new URI("uri"), "title", 0);
        });

        assertEquals("Bad Request: Illegal value in field: 'status', reason: mandatory " +
                "field 'status' must be specified", apiException.getMessage(), MESSAGE_NOT_AS_EXPECTED);

        assertEquals(BAD_REQUEST.getStatusCode(), apiException.getStatus(), STATUS_NOT_AS_EXPECTED);
    }
}
