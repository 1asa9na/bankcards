package com.example.bankcards.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.bankcards.entity.transfer.Transfer;

public interface TransferRepository {
    Optional<Transfer> findById(UUID id);
    List<Transfer> findAllBySrcAccountIdOrDestAccountId(Long accountId);
    void create(Transfer order);
    void update(Transfer order);
    void delete(Long id);
}
