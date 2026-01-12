package com.example.bankcards.service;

import com.example.bankcards.dto.auth.JwtRequest;
import com.example.bankcards.dto.auth.JwtResponse;

public interface AuthService {
    JwtResponse login(JwtRequest loginRequest);
    JwtResponse refresh(String refreshToken);
}
