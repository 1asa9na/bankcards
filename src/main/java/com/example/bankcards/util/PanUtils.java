package com.example.bankcards.util;

public class PanUtils {
    
    private final int VISIBLE = 4;

    public String mask(String pan) {
        if (pan == null || pan.length() < VISIBLE) {
            throw new IllegalArgumentException("Invalid PAN");
        }

        int maskedLength = pan.length() - VISIBLE;

        return "*".repeat(maskedLength) + pan.substring(maskedLength);
    }
}
