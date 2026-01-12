package com.example.bankcards.entity.order;

import lombok.Data;

@Data
public class Order {
    private OrderData orderData;
    private OrderStatus status;
}
