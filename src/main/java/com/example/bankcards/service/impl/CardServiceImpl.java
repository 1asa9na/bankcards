package com.example.bankcards.service.impl;

import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bankcards.entity.account.Account;
import com.example.bankcards.entity.card.Card;
import com.example.bankcards.entity.card.CardStatus;
import com.example.bankcards.exception.OperationDeniedException;
import com.example.bankcards.exception.ResourceNotFoundException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.service.AccountService;
import com.example.bankcards.service.CardService;
import com.example.bankcards.util.PanEncoder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final AccountService accountService;
    private final PanEncoder panEncoder;

    @Override
    @Transactional(readOnly = true)
    public Card getById(Long id) {
        return cardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Card not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Card> getAllByAccountId(Long accountId) {
        return cardRepository.findAllByAccountId(accountId);
    }

    @Override
    @Transactional
    public Card create(Card card, Long accountId) {
        if(card.getExpirationDate().isBefore(YearMonth.now())) {
            throw new OperationDeniedException("Card is expired.");
        }
        String pan = card.getPanEncrypted();
        card.setPanEncrypted(panEncoder.encrypt(pan));
        card.setPanHash(panEncoder.hash(pan));
        card.setPanMask(panEncoder.mask(pan));
        card.setStatus(CardStatus.STATUS_ACTIVE);
        Account account = accountService.getById(accountId);
        card.setAccount(account);
        cardRepository.save(card);
        return card;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        cardRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void block(Long id) {
        Card card = getById(id);
        card.setStatus(CardStatus.STATUS_BLOCKED);
        cardRepository.save(card);
    }

    @Override
    @Transactional
    public void unblock(Long id) {
        Card card = getById(id);
        if (card.getExpirationDate().isBefore(YearMonth.now())) {
            card.setStatus(CardStatus.STATUS_EXPIRED);
        } else {
            card.setStatus(CardStatus.STATUS_ACTIVE);
        }
        cardRepository.save(card);
    }
}
