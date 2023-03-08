package com.postnord.ndm.base.scrolling.model;


import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;


@QuarkusTest
class ScrollableResourceTest {

    public static final String TEST_URI = "/integration/test";
    public static final int SUCCESS_STATUS = 200;
    private static final String PARAMS = "status==ACTIVE&sort=+eventAt";

    @Test
    void testWhenUsingTotalCountResponseIsReturnedItContainsScrollingInformation() {
        given()
                .when()
                .get(TEST_URI + "/totalcount?" + PARAMS).then()
                .statusCode(SUCCESS_STATUS)
                .body("items[0].name", containsString("bosse"))
                .body("items[0].age", is(45))
                .body("items[1].name", containsString("anna"))
                .body("items[1].age", is(43));
    }

    @Test
    void testWhenUsingHasNextResponseIsReturnedItContainsScrollingInformation() {
        given()
                .when()
                .get(TEST_URI + "/hasnext?" + PARAMS).then()
                .statusCode(SUCCESS_STATUS)
                .body("items[0].name", containsString("bosse"))
                .body("items[0].age", is(45))
                .body("items[1].name", containsString("anna"))
                .body("items[1].age", is(43));
    }

}
