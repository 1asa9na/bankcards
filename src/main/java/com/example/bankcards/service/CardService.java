package com.example.bankcards.service;

import java.util.List;

import com.example.bankcards.entity.card.Card;

public interface CardService {
    Card getById(Long id);
    List<Card> getAllByAccountId(Long accountId);
    Card update(Card card);
    Card create(Card card, Long accountId);
    void delete(Long id);
}
