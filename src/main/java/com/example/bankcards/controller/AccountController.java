package com.example.bankcards.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankcards.dto.account.AccountDto;
import com.example.bankcards.dto.validation.OnUpdate;
import com.example.bankcards.entity.account.Account;
import com.example.bankcards.repository.mappers.AccountMapper;
import com.example.bankcards.service.AccountService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @PutMapping
    public AccountDto putMethodName(@Validated(OnUpdate.class) @RequestBody AccountDto dto) {
        Account account = accountMapper.toEntity(dto);
        Account updatedAccount = accountService.update(account);
        return accountMapper.toDto(updatedAccount);
    }

    @GetMapping("/{id}")
    public AccountDto getById(@PathVariable Long id) {
        Account account = accountService.getById(id);
        return accountMapper.toDto(account);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        accountService.delete(id);
    }

    @GetMapping("/{id}/cards")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
}
