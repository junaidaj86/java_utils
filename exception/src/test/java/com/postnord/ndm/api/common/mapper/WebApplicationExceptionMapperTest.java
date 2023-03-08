package com.postnord.ndm.api.common.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import io.quarkus.test.junit.QuarkusTest;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class WebApplicationExceptionMapperTest {

    private WebApplicationException webApplicationException;

    @Inject
    WebApplicationExceptionMapper webApplicationExceptionMapper;

    @BeforeEach
    void setUp() {
        webApplicationException = new ServerErrorException("don't panic just testing", INTERNAL_SERVER_ERROR);
    }

    @Test
    void whenMapperTranslatesFromWebApplicationExceptionThenResponseContainsCorrectHTTPStatusCode() {

        try (Response response = webApplicationExceptionMapper.toResponse(webApplicationException)) {

            assertEquals(INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus(), "Response status stored incorrectly");
        }
    }

    @Test
    void whenMapperTranslatesFromWebApplicationExceptionThenResponseContainsCorrectMessage() {

        try (Response response = webApplicationExceptionMapper.toResponse(webApplicationException)) {

            assertTrue(response.getEntity().toString().contains(INTERNAL_SERVER_ERROR.getReasonPhrase()),
                    "Response contained wrong APIError object (packed message incorrect)");
        }
    }

    @Test
    void whenMapperTranslatesFromWebApplicationExceptionThenResponseContainsCorrectErrorCode() {

        try (Response response = webApplicationExceptionMapper.toResponse(webApplicationException)) {

            assertTrue(response.getEntity().toString()
                            .contains(String.valueOf(INTERNAL_SERVER_ERROR.getStatusCode())),
                    "Response contained wrong APIError object (packed code incorrect)");
        }
    }
}
