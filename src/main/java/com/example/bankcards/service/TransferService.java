package com.example.bankcards.service;

import java.util.List;
import java.util.UUID;

import com.example.bankcards.entity.transfer.Transfer;

public interface TransferService {
    Transfer getById(UUID id);
    List<Transfer> getAllBySrcAccountIdOrDestAccountId(Long accountId);
    Transfer create(Transfer transfer);
}
