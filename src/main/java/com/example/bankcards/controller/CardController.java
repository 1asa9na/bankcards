package com.example.bankcards.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankcards.dto.card.CardDto;
import com.example.bankcards.entity.card.Card;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.mappers.CardMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;




@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
@Validated
public class CardController {

    private final CardService cardService;
    private final CardMapper cardMapper;

    @GetMapping("/{id}")
    @PreAuthorize("@customSecurityExpression:canAccessCard(#id)")
    public CardDto getById(@PathVariable Long id) {
        Card card = cardService.getById(id);
        return cardMapper.toDto(card);  
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("@customSecurityExpression:canAccessCard(#id)")
    public void delete(@PathVariable Long id) {
        cardService.delete(id);
    }

    @PostMapping("/{id}/block")
    @PreAuthorize("@customSecurityExpression:canAccessCard(#id")
    public void blockCard(@PathVariable Long id) {
        cardService.block(id);
    }

    @PostMapping("/{id}/unblock")
    @PreAuthorize("@customSecurityExpression:canUnblockCards(#id")
    public void unblockCard(@PathVariable Long id) {
        cardService.unblock(id);
    }
}
