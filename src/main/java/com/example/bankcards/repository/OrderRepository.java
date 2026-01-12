package com.example.bankcards.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.bankcards.entity.order.Order;

public interface OrderRepository {
    Optional<Order> findById(UUID id);
    List<Order> findAllBySrcAccountIdOrDestAccountId(Long accountId);
    void create(Order order);
    void update(Order order);
    void delete(Long id);
}
