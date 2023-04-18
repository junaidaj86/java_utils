package com.postnord.ndm.api.common.validation.validator;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.Matchers.is;

@QuarkusTest
@SuppressWarnings("PMD.TooManyStaticImports")
class ValidationResourceTest {
    static final String TEST_URI = "/integration/validation";
    private static final String UNSET = "UNSET";
    private static final String DETAIL = "detail";
    private static final String INSTANCE = "instance";
    private static final String PARAMETERS = "parameters";
    private static final String STATUS = "status";
    private static final String TITLE = "title";
    private static final String TYPE = "type";

    private static final String BAD_REQUEST_PROBLEM_TYPE = URI.create(
            "http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1").toString();

    private static final String VALID_UUID = "a25c04ea-cf4b-47b3-82d9-fa9ebf0b7a05";
    private static final String INVALID_UUID = "xxx";
    private static final String VALUE = "value";
    private static final String INVALID_REQUEST = "Invalid request";
    private static final String INVALID_PARAMS = "invalid-params";
    private static final String VALID_ALLOWED_EUICC = "1234567890123456789012345ABCDE";
    private static final String INVALID_NOT_ALLOWED_EUICC = "12345";
    private static final String VALID_ALLOWED_ICCID = "89430300000482738077"; //T-Mobile Austria
    private static final String INVALID_NOT_ALLOWED_ICCID = "89430300000482738073";//check digit incorrect
    private static final String VALID_ALLOWED_REGEX = "1h"; //valid regex is 1 digit and either m or h
    private static final String INVALID_NOT_ALLOWED_REGEX = "xxx10d";

    @Test
    void testWhenInvalidEuiccIsSpecifiedThenProblemIsReturnedCorrectly() {
        given()
                .when()
                .pathParam(VALUE, INVALID_NOT_ALLOWED_EUICC)
                .get(TEST_URI + "/euicc/{value}")
                .then()
                .statusCode(BAD_REQUEST.getStatusCode())
                .body(DETAIL, is(INVALID_REQUEST))
                .body(INSTANCE, is(UNSET))
                .body(PARAMETERS, is(Map.of(INVALID_PARAMS, List.of(getViolationObject("mustBeValidEuicc",
                        "Invalid IOTEidString. IOTEidString must be 20-30 hexadecimal characters or 32 hexadecimal characters in length.")))))
                .body(STATUS, is(BAD_REQUEST.getStatusCode()))
                .body(TITLE, is(BAD_REQUEST.getReasonPhrase()))
                .body(TYPE, is(BAD_REQUEST_PROBLEM_TYPE));
    }

    @Test
    void testWhenValidEuiccIsSpecifiedThenOKIsReturnedCorrectly() {
        given()
                .when()
                .pathParam(VALUE, VALID_ALLOWED_EUICC)
                .get(TEST_URI + "/euicc/{value}")
                .then()
                .statusCode(OK.getStatusCode())
                .body(is("Got EUICC of : " + VALID_ALLOWED_EUICC));
    }

    @Test
    void testWhenInvalidIccIdIsSpecifiedThenProblemIsReturnedCorrectly() {
        given()
                .when()
                .pathParam(VALUE, INVALID_NOT_ALLOWED_ICCID)
                .get(TEST_URI + "/iccid/{value}")
                .then()
                .statusCode(BAD_REQUEST.getStatusCode())
                .body(DETAIL, is(INVALID_REQUEST))
                .body(INSTANCE, is(UNSET))
                .body(PARAMETERS, is(Map.of(INVALID_PARAMS, List.of(getViolationObject("mustBeValidIccid",
                        "Invalid 'iccId'. It should be 19-20 characters in length.")))))
                .body(STATUS, is(BAD_REQUEST.getStatusCode()))
                .body(TITLE, is(BAD_REQUEST.getReasonPhrase()))
                .body(TYPE, is(BAD_REQUEST_PROBLEM_TYPE));
    }

    @Test
    void testWhenValidIccIdIsSpecifiedThenOKIsReturnedCorrectly() {
        given()
                .when()
                .pathParam(VALUE, VALID_ALLOWED_ICCID)
                .get(TEST_URI + "/iccid/{value}")
                .then()
                .statusCode(OK.getStatusCode())
                .body(is("Got ICCID of : " + VALID_ALLOWED_ICCID));
    }

    @Test
    void testWhenInvalidUuidSpecifiedThenProblemIsReturnedCorrectly() {
        given()
                .when()
                .pathParam(VALUE, INVALID_UUID)
                .get(TEST_URI + "/uuid/{value}")
                .then()
                .statusCode(BAD_REQUEST.getStatusCode())
                .body(DETAIL, is(INVALID_REQUEST))
                .body(INSTANCE, is(UNSET))
                .body(PARAMETERS, is(Map.of(INVALID_PARAMS, List.of(getViolationObject("mustBeUuid",
                        "String is not in UUID4 format.")))))
                .body(STATUS, is(BAD_REQUEST.getStatusCode()))
                .body(TITLE, is(BAD_REQUEST.getReasonPhrase()))
                .body(TYPE, is(BAD_REQUEST_PROBLEM_TYPE));
    }

    @Test
    void testWhenValidUuidSpecifiedThenOKIsReturnedCorrectly() {
        given()
                .when()
                .pathParam(VALUE, VALID_UUID)
                .get(TEST_URI + "/uuid/{value}")
                .then()
                .statusCode(OK.getStatusCode())
                .body(is("Got valid UUID of : " + VALID_UUID));
    }

    @Test
    void testWhenInvalidRegexSpecifiedThenProblemIsReturnedCorrectly() {
        given()
                .when()
                .pathParam(VALUE, INVALID_NOT_ALLOWED_REGEX)
                .get(TEST_URI + "/regex/{value}")
                .then()
                .statusCode(BAD_REQUEST.getStatusCode())
                .body(DETAIL, is(INVALID_REQUEST))
                .body(INSTANCE, is(UNSET))
                .body(PARAMETERS, is(Map.of(INVALID_PARAMS, List.of(getViolationObject("mustMatchRegex",
                        "must be 4m or 4h")))))
                .body(STATUS, is(BAD_REQUEST.getStatusCode()))
                .body(TITLE, is(BAD_REQUEST.getReasonPhrase()))
                .body(TYPE, is(BAD_REQUEST_PROBLEM_TYPE));
    }

    @Test
    void testWhenValidRegexSpecifiedThenOKIsReturnedCorrectly() {
        given()
                .when()
                .pathParam(VALUE, VALID_ALLOWED_REGEX)
                .get(TEST_URI + "/regex/{value}")
                .then()
                .statusCode(OK.getStatusCode())
                .body(is("Got valid REGEX of : " + VALID_ALLOWED_REGEX));
    }

    Map getViolationObject(final String name, final String message) {
        return Map.of("name", name, "violation", message);
    }
}