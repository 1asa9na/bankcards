package com.example.bankcards.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import com.example.bankcards.entity.account.Account;
import com.example.bankcards.entity.card.Card;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findAllByAccountId(Long userId);

    @Modifying
    @Query("""
            UPDATE Card c
            SET c.account = :account
            WHERE c.id = :id
            """)
    void assignToAccountById(@Param("id") Long id, @Param("account") Account account);
}
