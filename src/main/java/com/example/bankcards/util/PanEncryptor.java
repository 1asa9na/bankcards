package com.example.bankcards.util;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PanEncryptor {

    private final SecretKey keySpec;
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int IV_LENGTH = 16;

    private final SecureRandom secureRandom = new SecureRandom();

    public PanEncryptor(String secretKey) {
        if (secretKey.length() != 16 && secretKey.length() != 24 && secretKey.length() != 32) {
            throw new IllegalArgumentException("AES key must be 16, 24 or 32 bytes");
        }
        this.keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
    }

    public String encrypt(String pan) {
        try {
            byte[] iv = new byte[IV_LENGTH];
            secureRandom.nextBytes(iv);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encrypted = cipher.doFinal(pan.getBytes());

            // Объединяем IV + ciphertext для хранения
            byte[] encryptedWithIv = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, encryptedWithIv, 0, iv.length);
            System.arraycopy(encrypted, 0, encryptedWithIv, iv.length, encrypted.length);

            return Base64.getEncoder().encodeToString(encryptedWithIv);
        } catch (Exception e) {
            throw new RuntimeException("AES encryption failed", e);
        }
    }

    public String decrypt(String panEncrypted) {
        try {
            byte[] encryptedWithIv = Base64.getDecoder().decode(panEncrypted);

            byte[] iv = new byte[IV_LENGTH];
            byte[] encrypted = new byte[encryptedWithIv.length - IV_LENGTH];

            System.arraycopy(encryptedWithIv, 0, iv, 0, IV_LENGTH);
            System.arraycopy(encryptedWithIv, IV_LENGTH, encrypted, 0, encrypted.length);

            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

            byte[] decrypted = cipher.doFinal(encrypted);
            return new String(decrypted);
        } catch (Exception e) {
            throw new RuntimeException("AES decryption failed", e);
        }
    }
}
