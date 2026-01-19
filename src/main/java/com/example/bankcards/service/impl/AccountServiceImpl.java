package com.example.bankcards.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bankcards.entity.account.Account;
import com.example.bankcards.entity.user.User;
import com.example.bankcards.exception.ResourceNotFoundException;
import com.example.bankcards.repository.AccountRepository;
import com.example.bankcards.service.AccountService;
import com.example.bankcards.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public Account getById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAllByUserId(Long userId) {
        return accountRepository.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public Account update(Account account) {
        accountRepository.save(account);
        return account;
    }

    @Override
    @Transactional
    public Account create(Account account, Long userId) {
        User user = userService.getById(userId);
        account.setUser(user);
        account.setBalance(BigDecimal.ZERO);
        accountRepository.save(account);
        return account;
    }

    @Override
    @Transactional
    public void delete(Long accountId) {
        accountRepository.deleteById(accountId);
    }
    
}
