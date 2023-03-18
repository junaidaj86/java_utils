package com.postnord.lt;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.Response;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static com.postnord.helper.ConstantsHelper.CREDIT_CARD_PATH;
import static com.postnord.helper.ConstantsHelper.HTTP;
import static io.gatling.javaapi.core.CoreDsl.forAll;
import static io.gatling.javaapi.core.CoreDsl.global;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.core.OpenInjectionStep.atOnceUsers;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.restassured.RestAssured.given;
import static org.testcontainers.shaded.org.awaitility.Durations.FIVE_HUNDRED_MILLISECONDS;


public class BasicSimulation extends Simulation {

    int clients = 100;
    double requestsPerSecond = 100;
    int duration = 60;
    double expectedPercentageSuccess = 99;
    int ninetyFivePercentile = 400;     //200 milliseconds
    int ninetyNinePercentile = 500;     //500 milliseconds
    int oneHundredPercentile = 3000;   //3000 milliseconds

    GenericContainer container = new GenericContainer(DockerImageName
            .parse(System.getProperty("application_image")))
            .withStartupTimeout(Duration.ofMinutes(3l))
            .withExposedPorts(8080);

    static void waitForContainerStartup(final String host,
                                        final String port) {

        Awaitility.given().ignoreExceptions()
                .await()
                .atMost(3, TimeUnit.SECONDS)
                .with()
                .pollInterval(FIVE_HUNDRED_MILLISECONDS)
                .until(() -> Response.Status.OK.getStatusCode() ==
                        given()
                                .when()
                                .get(HTTP + host + ":" + port + CREDIT_CARD_PATH)
                                .getStatusCode());
    }

    {
        container.start();
        waitForContainerStartup(container.getHost(), String.valueOf(container.getMappedPort(8080)));

        HttpProtocolBuilder httpProtocol = http
                .contentTypeHeader("application/json;charset=UTF-8")
                .baseUrl(HTTP + container.getHost() + ":" + String.valueOf(container.getMappedPort(8080)));

        ScenarioBuilder scn = scenario("get credit information")
                .exec(http("request_1")
                        .get(CREDIT_CARD_PATH));

        setUp(scn.injectOpen(atOnceUsers(clients)))
                .protocols(httpProtocol)
                .assertions(
                        forAll().successfulRequests().percent().gte(expectedPercentageSuccess),
                        global().responseTime().percentile3().lt(ninetyFivePercentile), //95 percentile
                        global().responseTime().percentile4().lt(ninetyNinePercentile), //99 percentile
                        global().responseTime().max().lt(oneHundredPercentile),        //100 percentile
                        global().requestsPerSec().gte(requestsPerSecond) //make sure we handle the number of request per second
                );

    }

}
