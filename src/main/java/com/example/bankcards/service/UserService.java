package com.example.bankcards.service;

import com.example.bankcards.entity.user.User;

public interface UserService {
    User getById(Long id);
    User getByUsername(String username);
    User create(User user);
    User update(User user);
    User isAccountOwner(Long userId, Long accountId);
    void delete(Long id);
}
