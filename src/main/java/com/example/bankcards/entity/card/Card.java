package com.example.bankcards.entity.card;

import java.time.YearMonth;

import lombok.Data;

@Data
public class Card {

    private long id;
    private long accountId;
    private String panEncrypted;
    private String panHash;
    private String panMask;
    private YearMonth expirationDate;
    private CardStatus status;

}
