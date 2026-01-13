package com.example.bankcards.dto.user;

import org.hibernate.validator.constraints.Length;

import com.example.bankcards.dto.validation.OnCreate;
import com.example.bankcards.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserDto {

    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;

    @NotNull(message = "Name must be not null.", groups = {OnUpdate.class, OnCreate.class})
    @Length(max = 255, message = "Name is too long. Max length = 255")
    private String name;

    @NotNull(message = "Username must be not null.", groups = { OnUpdate.class, OnCreate.class })
    @Pattern(regexp = "[a-z][a-z0-9_]{5, 255}", message = "Username must contain only [a-z], digits or underscore and have length 5 - 255 symbols.")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must be not null.", groups = { OnUpdate.class, OnCreate.class })
    @Pattern(regexp = ".*[0-9].*", message = "Password must contain at least one digit.")
    @Length(min = 8, max = 16, message = "Password must be 8 - 16 characters long.")
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password confirmation must be not null.", groups = OnCreate.class)
    private String passwordConfirmation;
    
}
