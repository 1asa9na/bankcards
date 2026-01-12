package com.example.bankcards.dto.user;

import lombok.Data;

@Data
public class UserDto {

    private long id;
    private String name;
    private String username;
    private String password;
    private String passwordConfirmation;
    
}
