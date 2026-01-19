package com.example.bankcards.service.impl;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bankcards.entity.user.Role;
import com.example.bankcards.entity.user.User;
import com.example.bankcards.exception.ResourceNotFoundException;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public User update(User user) {
        userRepository.save(user);
        return user;
    }

    @Override
    @Transactional
    public User updateRoles(Long id, Set<Role> roles) {
        User user = getById(id);
        userRepository.deleteByUserId(id);
        for(Role role : roles) {
            userRepository.insertUserRole(id, role);
        }
        user.setRoles(roles);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isAccountOwner(Long userId, Long accountId) {
        User user = getById(userId);
        return userRoleRepository.isAccountOwner(user, accountId);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public User create(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalStateException("User already exists.");
        }
        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            throw new IllegalStateException("Password don't match.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPasswordConfirmation(null);
        userRepository.save(user);
        Set<Role> roles = Set.of(Role.ROLE_USER);
        userRepository.insertUserRole(user.getId(), Role.ROLE_USER);
        user.setRoles(roles);
        return user;
    }
    
}
