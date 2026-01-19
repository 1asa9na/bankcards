package com.example.bankcards.util;

import org.springframework.stereotype.Service;

@Service
public class PanEncoder {

    private final PanHasher panHasher;
    private final PanEncryptor panEncryptor;

    public PanEncoder(String aesKey, String hmacKey) {
        this.panHasher = new PanHasher(hmacKey);
        this.panEncryptor = new PanEncryptor(aesKey);
    }

    public String hash(String pan) {
        return panHasher.hash(pan);
    }

    public String encrypt(String pan) {
        return panEncryptor.encrypt(pan);
    }

    public String decrypt(String encryptedPan) {
        return panEncryptor.decrypt(encryptedPan);
    }

    public String mask(String pan) {
        if (pan == null || pan.length() < 4) {
            throw new IllegalArgumentException("Invalid PAN");
        }

        int visible = 4;
        int maskedLength = pan.length() - visible;

        return "*".repeat(maskedLength) + pan.substring(maskedLength);
    }
}