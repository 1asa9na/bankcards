package com.example.bankcards.security;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.bankcards.entity.user.Role;
import com.example.bankcards.entity.user.User;

public class JwtEntityFactory {

    public static JwtEntity create(User user) {
        return new JwtEntity(
            user.getId(),
            user.getName(),
            user.getUsername(),
            user.getEncryptedPassword(),
            mapToGrantedAuthorities(new ArrayList<>(user.getRoles()))
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles) {
        return roles
            .stream()
            .map(Role::name)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }
}
