package com.example.bankcards.dto.order;

import java.math.BigDecimal;
import java.util.UUID;

import com.example.bankcards.entity.order.OrderStatus;

import lombok.Data;

@Data
public class OrderDto {
    private UUID id;
    private Long SrcAccountId;
    private Long DestAccountId;
    private BigDecimal amount;
    private String currency;
    private OrderStatus status;
}
