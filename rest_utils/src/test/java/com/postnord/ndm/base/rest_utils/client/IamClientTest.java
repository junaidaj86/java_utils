package com.postnord.ndm.base.rest_utils.client;

import org.codehaus.plexus.util.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.wildfly.common.Assert;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;

import javax.inject.Inject;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

@QuarkusTest
@SuppressWarnings({"PMD.LinguisticNaming", "PMD.TooManyStaticImports", "PMD.ExcessiveImports"})
class IamClientTest {
    @Inject
    IamClient iamClient;

    @ConfigProperty(name = "com.postnord.ndm.base.rest_utils.client.iam.token.refresh-seconds")
    Integer refreshSeconds;

    @BeforeAll
    static void init() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void tokenShouldBeCachedWithinRefreshPeriod() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        final var firstToken = iamClient.getToken().await().indefinitely();
        final var secondToken = iamClient.getToken().await().indefinitely();

        Assert.assertNotNull(firstToken);
        Assert.assertTrue(StringUtils.isNotEmpty(firstToken));
        Assert.assertTrue(firstToken.equals(secondToken));
    }

    @Test
    void tokenShouldBeRefreshedAfterRefreshPeriod() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        final var firstToken = iamClient.getToken()
                .onItem().delayIt().by(Duration.ofSeconds(refreshSeconds + 1))
                .await().indefinitely();
        final var secondToken = iamClient.getToken().await().indefinitely();

        Assert.assertNotNull(firstToken);
        Assert.assertTrue(StringUtils.isNotEmpty(firstToken));
        Assert.assertFalse(firstToken.equals(secondToken));
    }
}
