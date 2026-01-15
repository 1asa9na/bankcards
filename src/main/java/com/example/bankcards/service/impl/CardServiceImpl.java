package com.example.bankcards.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bankcards.entity.card.Card;
import com.example.bankcards.service.CardService;

@Service
public class CardServiceImpl implements CardService {

    @Override
    public Card getById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public List<Card> getAllByAccountId(Long accountId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllByAccountId'");
    }

    @Override
    public Card update(Card card) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Card create(Card card, Long accountId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
