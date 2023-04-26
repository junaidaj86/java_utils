package com.postnord.ndm.base.logger;

import com.postnord.ndm.base.logger.model.LogRecord;
import com.postnord.ndm.base.logger.util.LoggerHelper;
import com.postnord.ndm.base.logger.util.PrivacyTagHelper;
import org.junit.jupiter.api.Assertions;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import java.util.Map;


@RequestScoped
@Path("/logger")
public class NdmLoggerResource {

    @GET
    @RolesAllowed("test")
    public void logTest() {
        final LogRecord logRecord = LogRecord
                .builder()
                .message("Test message")
                .extraData(PrivacyTagHelper.toExtraData(new PrivacyTaggedObject("123", "456", 789L)))
                .category("UT-test-category")
                .exception(new IllegalStateException("This is an exception"))
                .extraData(Map.of("account_number", "12345678", "billing_id", 12_345_678))
                .build();

        LoggerHelper.log(logRecord, (message, arguments) -> {
            Assertions.assertEquals("Test message", message, "Message should match expectation");
            Assertions.assertEquals(2, arguments.length, "Arguments should match expectation");
            NdmLogger.LOGGER.error(message, arguments);
        });
    }
}
