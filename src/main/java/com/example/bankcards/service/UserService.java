package com.example.bankcards.service;

import java.util.Set;
import java.util.UUID;

import com.example.bankcards.entity.user.Role;
import com.example.bankcards.entity.user.User;

public interface UserService {
    User getById(Long id);
    User getByUsername(String username);
    User create(User user);
    User update(User user);
    User updateRoles(Long id, Set<Role> roles);
    boolean isAccountOwner(Long userId, Long accountId);
    void delete(Long id);
    boolean isCardOwner(Long id, Long cardId);
    boolean isTransferParticipant(Long id, UUID transferId);
}
