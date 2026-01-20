package com.example.bankcards.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankcards.dto.account.AccountDto;
import com.example.bankcards.dto.user.UserDto;
import com.example.bankcards.dto.validation.OnCreate;
import com.example.bankcards.dto.validation.OnUpdate;
import com.example.bankcards.entity.account.Account;
import com.example.bankcards.entity.user.Role;
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
    @PreAuthorize("@customSecurityExpression:canAccessUser(#id)")
    public UserDto getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("@customSecurityExpression:canAccessUser(#id)")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @PutMapping
    @PreAuthorize("@customSecurityExpression:canAccessUser(#dto.id)")
    public UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto dto) {
        User user = userService.getById(dto.getId());
        user = userMapper.toEntity(dto, user);
        User updatedUser = userService.update(user);
        return userMapper.toDto(updatedUser);
    }

    @PostMapping("/{id}/accounts")
    @PreAuthorize("@customSecurityExpression:canAccessUser(#id)")
    public AccountDto createAccount(@PathVariable Long id, @Validated(OnCreate.class) @RequestBody AccountDto dto) {
        dto.setUserId(id);
        Account account = accountMapper.toEntity(dto);
        Account createdAccount = accountService.create(account, id);
        return accountMapper.toDto(createdAccount);
    }

    @PostMapping("/{id}/roles")
    @PreAuthorize("@customSecurityExpression:canInsertRoles(#id)")
    public UserDto insertRoles(@PathVariable Long id, @Validated(OnCreate.class) @RequestBody Set<String> rolesStrings) {
        Set<Role> roles = rolesStrings.stream().map(Role::valueOf).collect(Collectors.toSet());
        User user = userService.updateRoles(id, roles);
        return userMapper.toDto(user);
    }

    @GetMapping("/{id}/accounts")
    @PreAuthorize("@customSecurityExpression:canAccessUser(#id)")
    public List<AccountDto> getAccountsByUserId(@PathVariable Long id) {
        List<Account> accounts = accountService.getAllByUserId(id);
        return accountMapper.toDto(accounts);
    }
}
