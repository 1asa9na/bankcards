package com.example.bankcards.repository;

import java.util.List;
import java.util.Optional;

import com.example.bankcards.entity.card.Card;

public interface CardRepository {
    Optional<Card> findById(Long id);
    List<Card> findAllByUserId(Long userId);
    void assignToUserById(Long id, Long userId);
    void update(Card card);
    void create(Card card);
    void delete(Long id);
}
