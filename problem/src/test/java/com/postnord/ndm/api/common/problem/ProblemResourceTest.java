package com.postnord.ndm.api.common.problem;


import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import java.net.URI;

import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;
import static java.util.Collections.EMPTY_MAP;


@QuarkusTest
class ProblemResourceTest {

    //we know the /integration/problem endpoint will throw a problem response
    public static final String TEST_URI = "/integration/problem";
    public static final URI PROBLEM_TYPE = URI.create("http://ndm.postnord.com/problems/404");
    public static final URI PROBLEM_INSTANCE = URI.create("http://ndm.postnord.com/errors/404");
    public static final String PROBLEM_TITLE = "NOT_FOUND";
    public static final String PROBLEM_DETAIL = "ASSET NOT FOUND";
    public static final int PROBLEM_STATUS = 404;

    @Test
    void testWhenProblemOccursThenItIsReturnedCorrectly() {
        given()
                .when()
                .get(TEST_URI)
                .then()
                .statusCode(PROBLEM_STATUS)
                .body("detail", CoreMatchers.is(PROBLEM_DETAIL))
                .body("instance", CoreMatchers.is(PROBLEM_INSTANCE.toString()))
                .body("parameters", CoreMatchers.is(EMPTY_MAP))
                .body("status", CoreMatchers.is(PROBLEM_STATUS))
                .body("title", CoreMatchers.is(PROBLEM_TITLE))
                .body("type", CoreMatchers.is(PROBLEM_TYPE.toString()));
    }
}
