package com.postnord.ndm.base.jwt_helper;

import org.eclipse.microprofile.jwt.Claims;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

import javax.json.JsonObject;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;

/**
 * Utilities to generate tokens and key files in tests. The generated keys are stored in 'target/test-classes/' folder of the application
 * using this library.
 */
public final class JwtUtils {
    static final String RESOURCE_ACCESS_CLAIM = "resource_access";
    static final String OUTPUT_DIRECTORY = System.getProperty("test.output.directory");
    static final String PUBLIC_KEY = "/publicKey.pem";
    static final String PRIVATE_KEY = "/privateKey.pem";
    static final String JWT_TEMPLATE = "/JwtTemplate.json";

    static {
        init();
    }

    @SuppressWarnings("PMD.EmptyCatchBlock")
    static void init() {
        if (!new File(OUTPUT_DIRECTORY + PRIVATE_KEY).isFile() ||
                !new File(OUTPUT_DIRECTORY + PUBLIC_KEY).isFile()) {
            try {
                createKeyFiles();
            } catch (NoSuchAlgorithmException | IOException ignore) {
            }
        }
    }

    private JwtUtils() {
    }

    /**
     * Generate new token.
     *
     * @param expirySeconds     Validity time for generated token
     * @param resourceAccessMap Permissions per account for 'resource_access' claim
     * @return The generated token
     * @throws InvalidKeySpecException  Indicates JRE issues
     * @throws NoSuchAlgorithmException Indicates JRE issues
     * @throws IOException              Problems reading/writing to key files
     */
    public static String generateTokenString(final int expirySeconds,
                                             final Map<String, JsonObject> resourceAccessMap) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        return generateTokenString(expirySeconds, UUID.randomUUID(), resourceAccessMap);
    }

    /**
     * Generate new token.
     *
     * @param expirySeconds     Validity time for generated token
     * @param subject           User ID
     * @param resourceAccessMap Permissions per account for 'resource_access' claim
     * @return The generated token
     * @throws InvalidKeySpecException  Indicates JRE issues
     * @throws NoSuchAlgorithmException Indicates JRE issues
     * @throws IOException              Problems reading/writing to key files
     */
    public static String generateTokenString(final int expirySeconds,
                                             final UUID subject,
                                             final Map<String, JsonObject> resourceAccessMap) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        final PrivateKey pk = readPrivateKey();
        return generateTokenString(pk, expirySeconds, subject, resourceAccessMap);
    }

    private static String generateTokenString(final PrivateKey privateKey,
                                              final int expirySeconds,
                                              final UUID subject,
                                              final Map<String, JsonObject> resourceAccessMap) {
        final JwtClaimsBuilder claims = Jwt.claims(JWT_TEMPLATE);
        addTimeClaims(claims, expirySeconds);
        claims.subject(subject.toString());
        if (!resourceAccessMap.isEmpty()) {
            addResourceAccessClaim(claims, resourceAccessMap);
        }

        return claims.jws().keyId(PRIVATE_KEY).sign(privateKey);
    }

    private static void createKeyFiles() throws NoSuchAlgorithmException, IOException {
        final KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        final KeyPair kp = kpg.generateKeyPair();

        final StringBuilder privateKey = new StringBuilder(1024)
                .append("-----BEGIN PRIVATE KEY-----\n")
                .append(Base64.getMimeEncoder().encodeToString(kp.getPrivate().getEncoded()))
                .append("\n-----END PRIVATE KEY-----");
        writeFile(OUTPUT_DIRECTORY + PRIVATE_KEY, privateKey);

        final StringBuilder publicKey = new StringBuilder(1024)
                .append("-----BEGIN PUBLIC KEY-----\n")
                .append(Base64.getMimeEncoder().encodeToString(kp.getPublic().getEncoded()))
                .append("\n-----END PUBLIC KEY-----");
        writeFile(OUTPUT_DIRECTORY + PUBLIC_KEY, publicKey);
    }

    private static void addTimeClaims(final JwtClaimsBuilder claims, final int expirySeconds) {
        final long currentTimeInSecs = currentTimeInSecs();
        claims.issuedAt(currentTimeInSecs);
        claims.claim(Claims.auth_time.name(), currentTimeInSecs);
        claims.expiresAt(currentTimeInSecs + expirySeconds);
    }

    private static void addResourceAccessClaim(final JwtClaimsBuilder claims, final Map<String, JsonObject> resourceAccessMap) {
        claims.claim(RESOURCE_ACCESS_CLAIM, resourceAccessMap);
    }

    private static PrivateKey readPrivateKey() throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        try (InputStream contentIS = JwtUtils.class.getResourceAsStream(PRIVATE_KEY)) {
            final byte[] tmp = new byte[4096];
            final int length = contentIS.read(tmp);
            return decodePrivateKey(new String(tmp, 0, length, StandardCharsets.UTF_8));
        }
    }

    private static PrivateKey decodePrivateKey(final String pemEncoded) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final byte[] encodedBytes = toEncodedBytes(pemEncoded);
        final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
        final KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    private static byte[] toEncodedBytes(final String pemEncoded) {
        final String normalizedPem = removeBeginEnd(pemEncoded);
        return Base64.getDecoder().decode(normalizedPem);
    }

    private static String removeBeginEnd(final String pem) {
        return pem
                .replaceAll("-----BEGIN (.*)-----", "")
                .replaceAll("-----END (.*)----", "")
                .replaceAll("\r\n", "")
                .replaceAll("\n", "")
                .trim();
    }

    private static int currentTimeInSecs() {
        final long currentTimeMS = System.currentTimeMillis();
        return (int) (currentTimeMS / 1000);
    }

    // PATH_TRAVERSAL_OUT suppressed as file path cannot be controlled by the end user
    @SuppressWarnings({"PMD.AvoidFileStream", "findsecbugs:PATH_TRAVERSAL_OUT"})
    private static void writeFile(final String fileName, final StringBuilder content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content.toString());
            writer.flush();
        }
    }
}
