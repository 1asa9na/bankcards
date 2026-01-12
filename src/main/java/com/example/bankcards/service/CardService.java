package com.example.bankcards.service;

import java.util.List;

import com.example.bankcards.entity.card.Card;

public interface CardService {
    Card getById(Long id);
    List<Card> getAllByUserId(Long userId);
    Card update(Card card);
    Card create(Card card);
    void delete(Long id);
}
