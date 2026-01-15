package com.example.bankcards.security;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.example.bankcards.dto.auth.JwtResponse;
import com.example.bankcards.entity.user.Role;
import com.example.bankcards.entity.user.User;
import com.example.bankcards.exception.AccessDeniedException;
import com.example.bankcards.service.UserService;
import com.example.bankcards.service.props.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {
    
    private final JwtProperties jwtProperties;

    private final UserDetailsService userDetailsService;
    private final UserService userService;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(Long userId, String username, Set<Role> roles) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getAccess());
        return Jwts.builder()
            .claims()
                .subject(username)
                .add("id", userId)
                .add("roles", resolveRoles(roles))
                .and()
            .issuedAt(now)
            .expiration(validity)
            .signWith(key)
            .compact();
    }

    private List<String> resolveRoles(Set<Role> roles) {
        return roles.stream().map(Role::name).collect(Collectors.toList());
    }

    public String createRefreshToken(Long userId, String username) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtProperties.getRefresh());
        return Jwts.builder()
            .claims()
                .subject(username)
                .add("id", userId)
                .and()
            .issuedAt(now)
            .expiration(validity)
            .signWith(key)
            .compact();
    }

    public JwtResponse refreshUserTokens(String refreshToken) {
        JwtResponse jwtResponse = new JwtResponse();
        if(!validateToken(refreshToken)) {
            throw new AccessDeniedException();
        }
        Long userId = Long.valueOf(getId(refreshToken));
        User user = userService.getById(userId);
        jwtResponse.setId(userId);
        jwtResponse.setUsername(user.getUsername());
        jwtResponse.setAccessToken(createAccessToken(userId, user.getUsername(), user.getRoles()));
        jwtResponse.setRefreshToken(createRefreshToken(userId, user.getUsername()));
        return jwtResponse;
    }

    public boolean validateToken(String token) {
        Jws<Claims> claims = Jwts.parser().decryptWith(key).build().parseSignedClaims(token);
        return claims.getPayload().getExpiration().before(new Date());
    }

    private String getId(String token) {
        return Jwts.parser()
            .decryptWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .get("id")
            .toString();
    }

    private String getUsername(String token) {
        return Jwts.parser()
                .decryptWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject()
                .toString();
    }

    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getAuthorities());
    }
}
