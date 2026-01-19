package com.example.bankcards.service.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import com.example.bankcards.config.mapper.IgnoreUnmappedMapperConfig;
import com.example.bankcards.dto.card.CardDto;
import com.example.bankcards.entity.card.Card;
import com.example.bankcards.service.AccountService;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    config = IgnoreUnmappedMapperConfig.class,
    uses = AccountService.class
)
public interface CardMapper {

    @Mapping(target = "accountId", source = "account.id")
    CardDto toDto(Card card);

    List<CardDto> toDto(List<Card> cards);

    @Mapping(target = "account", source = "accountId")
    Card toEntity(CardDto cardDto);

    @Mapping(target = "account", source = "accountId")
    Card toEntity(CardDto cardDto, @MappingTarget Card card);
}
