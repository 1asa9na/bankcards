package com.example.bankcards.repository.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.example.bankcards.config.mapper.IgnoreUnmappedMapperConfig;
import com.example.bankcards.dto.card.CardDto;
import com.example.bankcards.entity.card.Card;
import com.example.bankcards.service.CardService;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    config = IgnoreUnmappedMapperConfig.class,
    uses = CardService.class
)
public interface CardMapper {

    @Mapping(target = "accountId", source = "account.id")
    CardDto toDto(Card card);

    @Mapping(target = "account", source = "accountId")
    Card toEntity(CardDto cardDto);
}
