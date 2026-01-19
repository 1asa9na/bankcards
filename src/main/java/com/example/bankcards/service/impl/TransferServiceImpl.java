package com.example.bankcards.service.impl;

import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.bankcards.entity.account.Account;
import com.example.bankcards.entity.card.CardStatus;
import com.example.bankcards.entity.transfer.Transfer;
import com.example.bankcards.entity.transfer.TransferStatus;
import com.example.bankcards.exception.OperationDeniedException;
import com.example.bankcards.exception.ResourceNotFoundException;
import com.example.bankcards.repository.TransferRepository;
import com.example.bankcards.service.TransferService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;

    @Override
    @Transactional(readOnly = true)
    public Transfer getById(UUID id) {
        return transferRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transfer not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transfer> getAllBySrcAccountIdOrDestAccountId(Long accountId) {
        return transferRepository.findAllBySrcCardIdOrDestCardId(accountId, accountId);
    }

    @Override
    @Transactional
    public Transfer create(Transfer transfer) {
        transferRepository.save(transfer);

        Account srcAccount = transfer.getSrcCard().getAccount();
        Account destAccount = transfer.getDestCard().getAccount();

        validateTransfer(transfer);
        srcAccount.setBalance(srcAccount.getBalance().subtract(transfer.getAmount()));
        destAccount.setBalance(destAccount.getBalance().add(transfer.getAmount()));
        transfer.setStatus(TransferStatus.COMPLETED);

        return transfer;
    }

    private void validateTransfer(Transfer transfer) {
        Account srcAccount = transfer.getSrcCard().getAccount();
        Account destAccount = transfer.getDestCard().getAccount();

        YearMonth now = YearMonth.now();

        if(transfer.getSrcCard().getExpirationDate().isBefore(now)) {
            throw new OperationDeniedException("Source card is expired.");
        }

        if(transfer.getDestCard().getExpirationDate().isBefore(now)) {
            throw new OperationDeniedException("Destination card is expired.");
        }

        if (transfer.getSrcCard().getStatus() != CardStatus.STATUS_ACTIVE) {
            throw new OperationDeniedException("Source card is not active.");
        }

        if(transfer.getDestCard().getStatus() != CardStatus.STATUS_ACTIVE) {
            throw new OperationDeniedException("Destination card is not active.");
        }

        if(srcAccount.getBalance().compareTo(transfer.getAmount()) < 0) {
            throw new OperationDeniedException("Insufficient funds");
        }

        if (srcAccount.equals(destAccount)) {
            throw new OperationDeniedException("Same account selected.");
        }

        if (srcAccount.getCurrency().equals(destAccount.getCurrency())) {
            throw new OperationDeniedException("Different currencies.");
        }

        if (!srcAccount.getUser().equals(destAccount.getUser())) {
            throw new OperationDeniedException("Only your accounts available.");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markFailed(UUID id) {
        Transfer t = transferRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Transfer does not exist."));
        t.setStatus(TransferStatus.FAILED);
    }
}
