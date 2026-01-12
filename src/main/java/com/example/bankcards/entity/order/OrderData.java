package com.example.bankcards.entity.order;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Value;

@Value
public class OrderData {
    private final UUID id;
    private final Long srcAccountId;
    private final Long destAccountId;
    private final BigDecimal amount;
    private final String currency;
}
