package com.example.bankcards.dto.account;

import java.math.BigDecimal;

import com.example.bankcards.dto.validation.OnCreate;
import com.example.bankcards.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountDto {

    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;

    @NotNull(message = "User id must be not null.", groups = {OnUpdate.class, OnCreate.class})
    private Long userId;

    @NotNull(message = "Currency must be not null.", groups = {OnUpdate.class, OnCreate.class})
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency is invalid.")
    private String currency;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal balance;
}
