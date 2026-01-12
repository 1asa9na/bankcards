package com.example.bankcards.dto.card;

import java.time.YearMonth;

import com.example.bankcards.entity.card.CardStatus;

import lombok.Data;

@Data
public class CardDto {

    private long id;
    private long accountId;
    private String panMask;
    private YearMonth expirationDate;
    private CardStatus status;

}
