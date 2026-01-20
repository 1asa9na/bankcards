package com.example.bankcards.entity.card;

import java.time.Instant;
import java.time.YearMonth;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.bankcards.entity.account.Account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cards")
@Getter
@Setter
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "pan_encrypted")
    private String panEncrypted;

    @Column(name = "pan_hash")
    private String panHash;

    @Column(name = "pan_mask")
    private String panMask;

    @Column(name = "expiration_date")
    private YearMonth expirationDate;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CardStatus status;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Instant updatedAt;
}
