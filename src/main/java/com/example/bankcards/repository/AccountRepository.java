package com.example.bankcards.repository;

import java.util.List;
import java.util.Optional;

import com.example.bankcards.entity.account.Account;

public interface AccountRepository {
    Optional<Account> findById(Long id);
    List<Account> findAllByUserId(Long userId);
    void update(Account account);
    void create(Account account);
    void delete(Long userId);
}
