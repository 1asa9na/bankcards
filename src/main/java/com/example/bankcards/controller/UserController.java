package com.example.bankcards.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankcards.dto.account.AccountDto;
import com.example.bankcards.dto.user.UserDto;
import com.example.bankcards.dto.validation.OnCreate;
import com.example.bankcards.dto.validation.OnUpdate;
import com.example.bankcards.entity.account.Account;
import com.example.bankcards.entity.user.User;
import com.example.bankcards.service.AccountService;
import com.example.bankcards.service.UserService;
import com.example.bankcards.service.mappers.AccountMapper;
import com.example.bankcards.service.mappers.UserMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    private final AccountService accountService;
    private final AccountMapper accountMapper;
    
    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @PutMapping
    public UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto dto) {
        User user = userMapper.toEntity(dto);
        User updatedUser = userService.update(user);
        return userMapper.toDto(updatedUser);
    }

    @PostMapping("/{id}/accounts")
    public AccountDto createAccount(@PathVariable Long id, @Validated(OnCreate.class) @RequestBody AccountDto dto) {
        Account account = accountMapper.toEntity(dto);
        Account createdAccount = accountService.create(account, id);
        return accountMapper.toDto(createdAccount);
    }

    @GetMapping("/{id}/accounts")
    public List<AccountDto> getAccountsByUserId(@PathVariable Long id) {
        List<Account> accounts = accountService.getAllByUserId(id);
        return accountMapper.toDto(accounts);
    }
}
