package com.example.bankcards.service;

import org.springframework.stereotype.Service;

import com.example.bankcards.entity.user.Role;

@Service
public interface RoleService {
    Role getByName(String name);
    Role getByUserId(Long id);
    Role getById(Integer id);
    Role create(Role role);
    Role update(Role role);
    void delete(Integer id);
}
