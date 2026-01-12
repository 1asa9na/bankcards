package com.example.bankcards.service;

import java.util.List;

import com.example.bankcards.entity.account.Account;

public interface AccountService {
    Account getById(Long id);
    List<Account> getAllByUserId(Long userId);
    Account update(Account account);
    Account create(Account account);
    void delete(Long userId);
}
