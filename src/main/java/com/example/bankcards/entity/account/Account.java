package com.example.bankcards.entity.account;

import java.math.BigDecimal;
import java.util.List;

import com.example.bankcards.entity.card.Card;

import lombok.Data;

@Data
public class Account {
    private long id;
    private long userId;
    private List<Card> cards;
    private String currency;
    private BigDecimal balance;
}
