package com.postnord.ndm.base.logger;

import com.postnord.ndm.base.logger.model.LogRecord;
import com.postnord.ndm.base.logger.util.LoggerHelper;
import com.postnord.ndm.base.logger.util.PrivacyTagHelper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;

@QuarkusTest
@SuppressWarnings("PMD.GuardLogStatement")
class NdmLoggerTest extends AbstractResourceTest {

    @Test
    void testLoggerResource() {
        LoggerHelper.log(
                LogRecord.builder()
                        .message("Executing test")
                        .category("UT-test-category")
                        .extraData(PrivacyTagHelper.toExtraData(new PrivacyTaggedObject("123", "456", 789L)))
                        .build(),
                (message, arguments) -> {
                    Assertions.assertEquals("Executing test", message);
                    Assertions.assertEquals(2, arguments.length, "Arguments should match expectation");
                    NdmLogger.LOGGER.debug(message, arguments);
                }
        );
        given()
                .when()
                .header("Authorization", generateTokenHeader())
                .header(X_FLOW_ID, UUID.randomUUID().toString())
                .get("/logger")
                .then()
                .statusCode(204);
        NdmLogger.debug(LogRecord.builder().message("Message succeeded").category("UT-test-category").build());
    }
}
