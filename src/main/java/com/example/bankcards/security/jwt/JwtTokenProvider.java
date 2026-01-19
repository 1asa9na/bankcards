package com.example.bankcards.security.jwt;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;
import com.example.bankcards.entity.user.Role;
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
            .signWith(key, 
                        Jwts.SIG.HS256)
            .compact();
    }

    

    public boolean validateToken(String token) {
        Jws<Claims> claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
        return claims.getPayload().getExpiration().after(new Date());
    }

    public String getId(String token) {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .get("id")
            .toString();
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject()
                .toString();
    }
}
