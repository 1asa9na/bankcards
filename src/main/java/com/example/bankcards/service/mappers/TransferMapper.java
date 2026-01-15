package com.example.bankcards.service.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.example.bankcards.config.mapper.IgnoreUnmappedMapperConfig;
import com.example.bankcards.dto.transfer.TransferDto;
import com.example.bankcards.entity.transfer.Transfer;
import com.example.bankcards.service.CardService;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    config = IgnoreUnmappedMapperConfig.class,
    uses = CardService.class
)
public interface TransferMapper {
    
    @Mapping(target = "srcCardId", source = "srcCard.id")
    @Mapping(target = "destCardId", source = "destCard.id")
    TransferDto toDto(Transfer transfer);

    List<TransferDto> toDto(List<Transfer> transfers);

    @Mapping(target = "srcCard", source = "srcCardId")
    @Mapping(target = "destCard", source = "destCardId")
    Transfer toEntity(TransferDto transferDto);
}
