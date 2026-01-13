package com.example.bankcards.repository;

import java.util.Optional;

import com.example.bankcards.entity.user.Role;
import com.example.bankcards.entity.user.User;

public interface UserRepository {
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    void update(User user);
    void create(User user);
    void insertUserRole(Long userId, Role role);
    void isAccountOwner(Long userId, Long accountId);
    void delete(Long userId);
}
