package com.example.bankcards.dto.account;

import lombok.Data;

@Data
public class AccountDto {
    private long id;
    private long userId;
    private String currency;
    private double balance;
}
