package com.example.bankcards.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.bankcards.dto.auth.JwtRequest;
import com.example.bankcards.dto.auth.JwtResponse;
import com.example.bankcards.entity.user.User;
import com.example.bankcards.security.JwtTokenProvider;
import com.example.bankcards.service.AuthService;
import com.example.bankcards.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authManager;
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;

    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        JwtResponse resp = new JwtResponse();
        authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        User user = userService.getByUsername(loginRequest.getUsername());
        resp.setId(user.getId());
        resp.setUsername(user.getUsername());
        resp.setAccessToken(tokenProvider.createAccessToken(user.getId(), user.getUsername(), user.getRoles()));
        resp.setRefreshToken(tokenProvider.createRefreshToken(user.getId(), user.getUsername()));
        return resp;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return tokenProvider.refreshUserTokens(refreshToken);
    }
    
}
