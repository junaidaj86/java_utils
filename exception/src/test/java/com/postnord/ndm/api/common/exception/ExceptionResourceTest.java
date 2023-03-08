package com.postnord.ndm.api.common.exception;


import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.List;
import java.util.Map;

import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;
import static java.util.Collections.EMPTY_MAP;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.METHOD_NOT_ALLOWED;
import static javax.ws.rs.core.Response.Status.NOT_ACCEPTABLE;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static javax.ws.rs.core.Response.Status.UNSUPPORTED_MEDIA_TYPE;


@QuarkusTest
@SuppressWarnings("PMD.TooManyStaticImports")
class ExceptionResourceTest {

    //we know the /integration/problem endpoint will throw a problem response
    private static final String TEST_URI = "/integration/exception";
    private static final String UNSET = "UNSET";
    private static final String DETAIL = "detail";
    private static final String INSTANCE = "instance";
    private static final String PARAMETERS = "parameters";
    private static final String STATUS = "status";
    private static final String TITLE = "title";
    private static final String TYPE = "type";

    private static final String INTERNAL_SERVER_ERROR_TYPE = URI.create(
            "http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.1").toString();
    private static final String API_PROBLEM_TYPE = URI.create(
            "http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1").toString();
    private static final String FORBIDDEN_PROBLEM_TYPE = URI.create(
            "http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.4").toString();
    private static final String NOT_ACCEPTABLE_PROBLEM_TYPE = URI.create(
            "http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.7").toString();
    private static final String NOT_ALLOWED_PROBLEM_TYPE = URI.create(
            "http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.6").toString();
    private static final String NOT_AUTHORIZED_PROBLEM_TYPE = URI.create(
            "http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.2").toString();
    private static final String NOT_FOUND_PROBLEM_TYPE = URI.create(
            "http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.5").toString();
    private static final String UNSUPPORTED_MEDIA_TYPE_PROBLEM_TYPE = URI.create(
            "http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.16").toString();
    private static final String SERVICE_UNAVAILABILITY_PROBLEM_TYPE = URI.create(
            "http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.5.4").toString();
    private static final String BAD_REQUEST_PROBLEM_TYPE = URI.create(
            "http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1").toString();

    @Test
    void testWhenWebApplicationExceptionOccursThenItIsReturnedCorrectly() {
        given()
                .when()
                .get(TEST_URI + "/webapplication")
                .then()
                .statusCode(INTERNAL_SERVER_ERROR.getStatusCode())
                .body(DETAIL, CoreMatchers.is(INTERNAL_SERVER_ERROR.getReasonPhrase()))
                .body(INSTANCE, CoreMatchers.is(UNSET))
                .body(PARAMETERS, CoreMatchers.is(EMPTY_MAP))
                .body(STATUS, CoreMatchers.is(INTERNAL_SERVER_ERROR.getStatusCode()))
                .body(TITLE, CoreMatchers.is(INTERNAL_SERVER_ERROR.getReasonPhrase()))
                .body(TYPE, CoreMatchers.is(INTERNAL_SERVER_ERROR_TYPE));
    }

    @Test
    void testWhenServiceUnavailableExceptionOccursThenItIsReturnedCorrectly() {
        given()
                .when()
                .get(TEST_URI + "/serviceunavailable")
                .then()
                .statusCode(SERVICE_UNAVAILABLE.getStatusCode())
                .body(DETAIL, CoreMatchers.is(SERVICE_UNAVAILABLE.getReasonPhrase()))
                .body(INSTANCE, CoreMatchers.is(UNSET))
                .body(PARAMETERS, CoreMatchers.is(EMPTY_MAP))
                .body(STATUS, CoreMatchers.is(SERVICE_UNAVAILABLE.getStatusCode()))
                .body(TITLE, CoreMatchers.is(SERVICE_UNAVAILABLE.getReasonPhrase()))
                .body(TYPE, CoreMatchers.is(SERVICE_UNAVAILABILITY_PROBLEM_TYPE));
    }

    @Test
    void testWhenNotSupportedExceptionOccursThenItIsReturnedCorrectly() {
        given()
                .when()
                .get(TEST_URI + "/notsupported")
                .then()
                .statusCode(UNSUPPORTED_MEDIA_TYPE.getStatusCode())
                .body(DETAIL, CoreMatchers.is(UNSUPPORTED_MEDIA_TYPE.getReasonPhrase()))
                .body(INSTANCE, CoreMatchers.is(UNSET))
                .body(PARAMETERS, CoreMatchers.is(EMPTY_MAP))
                .body(STATUS, CoreMatchers.is(UNSUPPORTED_MEDIA_TYPE.getStatusCode()))
                .body(TITLE, CoreMatchers.is(UNSUPPORTED_MEDIA_TYPE.getReasonPhrase()))
                .body(TYPE, CoreMatchers.is(UNSUPPORTED_MEDIA_TYPE_PROBLEM_TYPE));
    }

    @Test
    void testWhenNotFoundExceptionOccursThenItIsReturnedCorrectly() {
        given()
                .when()
                .get(TEST_URI + "/notfound")
                .then()
                .statusCode(NOT_FOUND.getStatusCode())
                .body(DETAIL, CoreMatchers.is(NOT_FOUND.getReasonPhrase()))
                .body(INSTANCE, CoreMatchers.is(UNSET))
                .body(PARAMETERS, CoreMatchers.is(EMPTY_MAP))
                .body(STATUS, CoreMatchers.is(NOT_FOUND.getStatusCode()))
                .body(TITLE, CoreMatchers.is(NOT_FOUND.getReasonPhrase()))
                .body(TYPE, CoreMatchers.is(NOT_FOUND_PROBLEM_TYPE));
    }

    @Test
    void testWhenNotAuthorizedExceptionOccursThenItIsReturnedCorrectly() {
        given()
                .when()
                .get(TEST_URI + "/notauthorized")
                .then()
                .statusCode(UNAUTHORIZED.getStatusCode())
                .body(DETAIL, CoreMatchers.is(UNAUTHORIZED.getReasonPhrase()))
                .body(INSTANCE, CoreMatchers.is(UNSET))
                .body(PARAMETERS, CoreMatchers.is(EMPTY_MAP))
                .body(STATUS, CoreMatchers.is(UNAUTHORIZED.getStatusCode()))
                .body(TITLE, CoreMatchers.is(UNAUTHORIZED.getReasonPhrase()))
                .body(TYPE, CoreMatchers.is(NOT_AUTHORIZED_PROBLEM_TYPE));
    }

    @Test
    void testWhenNotAllowedExceptionOccursThenItIsReturnedCorrectly() {
        given()
                .when()
                .get(TEST_URI + "/notallowed")
                .then()
                .statusCode(METHOD_NOT_ALLOWED.getStatusCode())
                .body(DETAIL, CoreMatchers.is(METHOD_NOT_ALLOWED.getReasonPhrase()))
                .body(INSTANCE, CoreMatchers.is(UNSET))
                .body(PARAMETERS, CoreMatchers.is(EMPTY_MAP))
                .body(STATUS, CoreMatchers.is(METHOD_NOT_ALLOWED.getStatusCode()))
                .body(TITLE, CoreMatchers.is(METHOD_NOT_ALLOWED.getReasonPhrase()))
                .body(TYPE, CoreMatchers.is(NOT_ALLOWED_PROBLEM_TYPE));
    }

    @Test
    void testWhenNotAcceptableExceptionOccursThenItIsReturnedCorrectly() {
        given()
                .when()
                .get(TEST_URI + "/notacceptable")
                .then()
                .statusCode(NOT_ACCEPTABLE.getStatusCode())
                .body(DETAIL, CoreMatchers.is(NOT_ACCEPTABLE.getReasonPhrase()))
                .body(INSTANCE, CoreMatchers.is(UNSET))
                .body(PARAMETERS, CoreMatchers.is(EMPTY_MAP))
                .body(STATUS, CoreMatchers.is(NOT_ACCEPTABLE.getStatusCode()))
                .body(TITLE, CoreMatchers.is(NOT_ACCEPTABLE.getReasonPhrase()))
                .body(TYPE, CoreMatchers.is(NOT_ACCEPTABLE_PROBLEM_TYPE));
    }

    @Test
    void testWhenForbiddenExceptionOccursThenItIsReturnedCorrectly() {
        given()
                .when()
                .get(TEST_URI + "/forbidden")
                .then()
                .statusCode(FORBIDDEN.getStatusCode())
                .body(DETAIL, CoreMatchers.is(FORBIDDEN.getReasonPhrase()))
                .body(INSTANCE, CoreMatchers.is(UNSET))
                .body(PARAMETERS, CoreMatchers.is(EMPTY_MAP))
                .body(STATUS, CoreMatchers.is(FORBIDDEN.getStatusCode()))
                .body(TITLE, CoreMatchers.is(FORBIDDEN.getReasonPhrase()))
                .body(TYPE, CoreMatchers.is(FORBIDDEN_PROBLEM_TYPE));
    }

    @Test
    void testWhenConstraintViolationExceptionOccursThenItIsReturnedCorrectly() {
        final Map<String, String> violationObject = Map.of("name", "field", "violation", "Don't panic just testing");
        given()
                .when()
                .get(TEST_URI + "/constraintviolation")
                .then()
                .statusCode(BAD_REQUEST.getStatusCode())
                .body(DETAIL, CoreMatchers.is("Invalid request"))
                .body(INSTANCE, CoreMatchers.is(UNSET))
                .body(PARAMETERS, CoreMatchers.is(Map.of("invalid-params", List.of(violationObject))))
                .body(STATUS, CoreMatchers.is(BAD_REQUEST.getStatusCode()))
                .body(TITLE, CoreMatchers.is(BAD_REQUEST.getReasonPhrase()))
                .body(TYPE, CoreMatchers.is(BAD_REQUEST_PROBLEM_TYPE));
    }

    @Test
    void testWhenAPIExceptionOccursThenItIsReturnedCorrectly() {
        given()
                .when()
                .get(TEST_URI + "/api")
                .then()
                .statusCode(UNSUPPORTED_MEDIA_TYPE.getStatusCode())
                .body(DETAIL, CoreMatchers.is("Don't panic just testing"))
                .body(INSTANCE, CoreMatchers.is(UNSET))
                .body(PARAMETERS, CoreMatchers.is(EMPTY_MAP))
                .body(STATUS, CoreMatchers.is(UNSUPPORTED_MEDIA_TYPE.getStatusCode()))
                .body(TITLE, CoreMatchers.is(UNSUPPORTED_MEDIA_TYPE.getReasonPhrase()))
                .body(TYPE, CoreMatchers.is(API_PROBLEM_TYPE));
    }

    @Test
    void testWhenUnhandledExceptionOccursThenItIsReturnedCorrectly() {
        given()
                .when()
                .get(TEST_URI + "/unhandled")
                .then()
                .statusCode(INTERNAL_SERVER_ERROR.getStatusCode())
                .body(DETAIL, CoreMatchers.is(INTERNAL_SERVER_ERROR.getReasonPhrase()))
                .body(INSTANCE, CoreMatchers.is(UNSET))
                .body(PARAMETERS, CoreMatchers.is(EMPTY_MAP))
                .body(STATUS, CoreMatchers.is(INTERNAL_SERVER_ERROR.getStatusCode()))
                .body(TITLE, CoreMatchers.is(INTERNAL_SERVER_ERROR.getReasonPhrase()))
                .body(TYPE, CoreMatchers.is(INTERNAL_SERVER_ERROR_TYPE));
    }
}
