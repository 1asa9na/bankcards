package com.example.bankcards.util;

import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PanHasher {

    private static final String HMAC_ALGO = "HmacSHA256";

    private final SecretKeySpec keySpec;

    public PanHasher(String secretKey) {
        this.keySpec = new SecretKeySpec(secretKey.getBytes(), HMAC_ALGO);
    }

    public String hash(String pan) {
        try {
            Mac mac = Mac.getInstance(HMAC_ALGO);
            mac.init(keySpec);
            byte[] rawHmac = mac.doFinal(pan.getBytes());

            // Можно hex, но Base64 компактнее
            return Base64.getEncoder().encodeToString(rawHmac);
        } catch (Exception e) {
            throw new IllegalStateException("HMAC hashing failed", e);
        }
    }
    
}
