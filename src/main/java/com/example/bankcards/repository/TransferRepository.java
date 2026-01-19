package com.example.bankcards.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bankcards.entity.transfer.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, UUID> {

    List<Transfer> findAllBySrcCardIdOrDestCardId(Long srcCardId, Long destCardId);
}
