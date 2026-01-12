package com.example.bankcards.service.impl;

import org.springframework.stereotype.Service;

import com.example.bankcards.dto.auth.JwtRequest;
import com.example.bankcards.dto.auth.JwtResponse;
import com.example.bankcards.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{

    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'refresh'");
    }
    
}
