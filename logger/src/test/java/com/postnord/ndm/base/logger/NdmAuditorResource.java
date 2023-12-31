package com.postnord.ndm.base.logger;

import com.postnord.ndm.base.logger.model.LogRecord;
import com.postnord.ndm.base.logger.util.LoggerHelper;
import com.postnord.ndm.base.logger.util.PrivacyTagHelper;

import org.junit.jupiter.api.Assertions;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;


@RequestScoped
@Path("/auditor")
public class NdmAuditorResource {

    @GET
    @RolesAllowed("test")
    public void logTest() {
        final LogRecord logRecord = LogRecord
                .builder()
                .message("Test message")
                .category("UT-test-category")
                .extraData(PrivacyTagHelper.toExtraData(new PrivacyTaggedObject("123", "456", 789L)))
                .build();

        LoggerHelper.log(logRecord, (message, arguments) -> {
            Assertions.assertEquals("Test message", message, "Message should match expectation");
            Assertions.assertEquals(2, arguments.length, "Arguments should match expectation");
            NdmAuditor.LOGGER.info(message, arguments);
        });
    }
}
