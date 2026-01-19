package com.example.bankcards.controller;

import java.util.UUID;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankcards.dto.transfer.TransferDto;
import com.example.bankcards.entity.transfer.Transfer;
import com.example.bankcards.service.TransferService;
import com.example.bankcards.service.mappers.TransferMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/transfers")
@RequiredArgsConstructor
@Validated
public class TransferController {
    
    private final TransferService transferService;
    private final TransferMapper transferMapper;

    @GetMapping("/{id}")
    public TransferDto getbyId(@PathVariable UUID id) {
        Transfer transfer = transferService.getById(id);
        return transferMapper.toDto(transfer);
    }

    @PostMapping
    public TransferDto create(@Validated @RequestBody TransferDto dto) {
        Transfer transfer = transferMapper.toEntity(dto);
        Transfer createdTransfer = transferService.create(transfer);
        return transferMapper.toDto(createdTransfer);
    }
}
