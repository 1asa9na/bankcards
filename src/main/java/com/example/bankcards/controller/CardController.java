package com.example.bankcards.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankcards.dto.card.CardDto;
import com.example.bankcards.dto.validation.OnUpdate;
import com.example.bankcards.entity.card.Card;
import com.example.bankcards.service.CardService;
import com.example.bankcards.service.mappers.CardMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
@Validated
public class CardController {

    private final CardService cardService;
    private final CardMapper cardMapper;
    
    @PutMapping
    public CardDto update(@Validated(OnUpdate.class) @RequestBody CardDto dto) {
        Card card = cardMapper.toEntity(dto);
        Card updatedCard = cardService.update(card);
        return cardMapper.toDto(updatedCard);
    }

    @GetMapping("/{id}")
    public CardDto getById(@PathVariable Long id) {
        Card card = cardService.getById(id);
        return cardMapper.toDto(card);  
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cardService.delete(id);
    }
    
}
