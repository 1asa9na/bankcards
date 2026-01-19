package com.example.bankcards.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankcards.dto.account.AccountDto;
import com.example.bankcards.dto.card.CardDto;
import com.example.bankcards.dto.validation.OnCreate;
import com.example.bankcards.dto.validation.OnUpdate;
import com.example.bankcards.entity.account.Account;
import com.example.bankcards.entity.card.Card;
import com.example.bankcards.service.AccountService;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.mappers.AccountMapper;
import com.example.bankcards.service.mappers.CardMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Validated
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final CardService cardService;
    private final CardMapper cardMapper;

    @PutMapping
    public AccountDto update(@Validated(OnUpdate.class) @RequestBody AccountDto dto) {
        Account account = accountService.getById(dto.getId());
        account = accountMapper.toEntity(dto, account);
        Account updatedAccount = accountService.update(account);
        return accountMapper.toDto(updatedAccount);
    }

    @GetMapping("/{id}")
    public AccountDto getById(@PathVariable Long id) {
        Account account = accountService.getById(id);
        return accountMapper.toDto(account);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        accountService.delete(id);
    }

    @GetMapping("/{id}/cards")
    public List<CardDto> getCardsByAccountId(@PathVariable Long id) {
        List<Card> cards = cardService.getAllByAccountId(id);
        return cardMapper.toDto(cards);
    }
    
    @PostMapping("/{id}/cards")
    public CardDto createCard(@PathVariable Long id, @Validated(OnCreate.class) @RequestBody CardDto dto) {
        dto.setAccountId(id);
        Card card = cardMapper.toEntity(dto);
        Card createdCard = cardService.create(card, id);
        return cardMapper.toDto(createdCard);
    }
}
