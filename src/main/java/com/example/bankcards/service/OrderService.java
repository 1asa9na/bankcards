package com.example.bankcards.service;

import java.util.List;
import java.util.UUID;

import com.example.bankcards.entity.order.Order;

public interface OrderService {
    Order getById(UUID id);
    List<Order> getAllBySrcAccountIdOrDestAccountId(Long accountId);
    Order create(Order order);
    Order update(Order order);
    void delete(Long id);
}
