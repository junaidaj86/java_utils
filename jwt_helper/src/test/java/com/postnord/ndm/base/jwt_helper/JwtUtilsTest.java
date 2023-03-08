package com.postnord.ndm.base.jwt_helper;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.Json;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static com.postnord.ndm.base.jwt_helper.JwtUtils.OUTPUT_DIRECTORY;
import static com.postnord.ndm.base.jwt_helper.JwtUtils.PRIVATE_KEY;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("PMD.TooManyStaticImports")
class JwtUtilsTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtilsTest.class);

    @Test
    void testGenerateTokenStringShouldSucceed() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        final String token = JwtUtils.generateTokenString(
                300,
                Map.of(UUID.randomUUID().toString(), Json.createObjectBuilder().add("roles", Json.createArrayBuilder().add("test").build()).build())
        );
        LOGGER.debug("Created token: {}", token);
        assertNotNull(token, "Token is null");
        assertTrue(decodeTokenPayload(token).contains("\"resource_access\""));
        assertTrue(decodeTokenPayload(token).contains("\"upn\""));
        assertTrue(decodeTokenPayload(token).contains("\"iss\""));
    }

    @Test
    void testGenerateTokenStringWithNoAccountsShouldSucceed() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        final String token = JwtUtils.generateTokenString(
                300,
                Collections.emptyMap()
        );
        LOGGER.debug("Created token: {}", token);
        assertNotNull(token, "Token is null");
        assertFalse(decodeTokenPayload(token).contains("\"resource_access\""));
        assertTrue(decodeTokenPayload(token).contains("\"upn\""));
        assertTrue(decodeTokenPayload(token).contains("\"iss\""));
    }

    @Test
    @SuppressWarnings("PMD.UseAssertEqualsInsteadOfAssertTrue")
    void shouldNotGenerateKeyIfKeyExistsAndIntact() throws NoSuchAlgorithmException, IOException {
        final var md5 = MessageDigest.getInstance("MD5");
        md5.update(Files.readAllBytes(Paths.get(OUTPUT_DIRECTORY + PRIVATE_KEY)));
        final byte[] digestBefore = md5.digest();

        JwtUtils.init();
        md5.update(Files.readAllBytes(Paths.get(OUTPUT_DIRECTORY + PRIVATE_KEY)));
        final byte[] digestAfter = md5.digest();

        assertEquals(new String(digestBefore), new String(digestAfter));
    }

    private String decodeTokenPayload(final String token) {
        final String[] chunks = token.split("\\.");
        return new String(Base64.getDecoder().decode(chunks[1]));
    }
}
