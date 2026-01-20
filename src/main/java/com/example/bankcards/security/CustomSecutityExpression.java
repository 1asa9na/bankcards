package com.example.bankcards.security;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.bankcards.entity.user.Role;
import com.example.bankcards.security.jwt.JwtEntity;
import com.example.bankcards.service.UserService;

import lombok.RequiredArgsConstructor;

@Service("customSecurityExpression")
@RequiredArgsConstructor
public class CustomSecutityExpression {
    
    private final UserService userService;

    public boolean canAccessUser(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JwtEntity user = (JwtEntity) auth.getAuthorities();
        Long userId = user.getId();

        return userId.equals(id) || hasAnyRole(auth, Role.ROLE_ADMIN);
    }

    private boolean hasAnyRole(Authentication auth, Role... roles) {
        for (Role role: roles) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
            if(auth.getAuthorities().contains(authority)) {
                return true;
            }
        }
        return false;
    }

    public boolean canAccessAccount(Long accountId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JwtEntity user = (JwtEntity) auth.getAuthorities();
        Long id = user.getId();

        return userService.isAccountOwner(id, accountId) || hasAnyRole(auth, Role.ROLE_ADMIN);
    }

    public boolean canAccessCard(Long cardId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JwtEntity user = (JwtEntity) auth.getAuthorities();
        Long id = user.getId();

        return userService.isCardOwner(id, cardId) || hasAnyRole(auth, Role.ROLE_ADMIN);
    }

    public boolean canInsertRoles(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return hasAnyRole(auth, Role.ROLE_ADMIN);
    }

    public boolean canUnblockCards(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return hasAnyRole(auth, Role.ROLE_ADMIN);
    }

    public boolean canAccessTransfer(UUID transferId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JwtEntity user = (JwtEntity) auth.getAuthorities();
        Long id = user.getId();

        return userService.isTransferParticipant(id, transferId) || hasAnyRole(auth, Role.ROLE_ADMIN);
    }
}
