package com.postnord.ndm.base.jsonb_utils;


import java.io.ByteArrayInputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import jakarta.inject.Singleton;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonPatch;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import static com.postnord.ndm.base.jsonb_utils.ConstantsHelper.HEADER;
import static com.postnord.ndm.base.jsonb_utils.ConstantsHelper.HEADER_SIGNATURE;
import static com.postnord.ndm.base.jsonb_utils.ConstantsHelper.HMAC_SHA_256;
import static com.postnord.ndm.base.jsonb_utils.ConstantsHelper.SIGNATURE;
import static java.nio.charset.StandardCharsets.UTF_8;

@Singleton
@SuppressWarnings({"PMD.AvoidDuplicateLiterals", "PMD.TooManyStaticImports"})
public class JsonHmacUtil {

    public byte[] signMessage(final byte[] message, final byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        final JsonObject json = Json.createReader(new ByteArrayInputStream(message)).readObject();
        final Mac sha256HMAC = Mac.getInstance(HMAC_SHA_256);
        final SecretKeySpec secretKey = new SecretKeySpec(key, HMAC_SHA_256);
        sha256HMAC.init(secretKey);
        final String hash = Base64.getEncoder().encodeToString(sha256HMAC.doFinal(json.toString().getBytes(UTF_8)));
        final JsonPatch path = Json.createPatchBuilder()
                .add(HEADER_SIGNATURE, hash)
                .build();
        return path.apply(json).toString().getBytes(UTF_8);
    }

    public boolean validateMessage(final byte[] message, final byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        final JsonObject json = Json.createReader(new ByteArrayInputStream(message)).readObject();
        final Mac mac = Mac.getInstance(HMAC_SHA_256);
        final SecretKeySpec secretKey = new SecretKeySpec(key, HMAC_SHA_256);
        mac.init(secretKey);
        final JsonPatch path = Json.createPatchBuilder()
                .remove(HEADER_SIGNATURE)
                .build();
        final String msg = path.apply(json).toString();
        final String hash = Base64.getEncoder().encodeToString(mac.doFinal(msg.getBytes(UTF_8)));
        return json.getJsonObject(HEADER).getString(SIGNATURE).equals(hash);
    }

}
