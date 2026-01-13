package com.example.bankcards.repository.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.example.bankcards.config.mapper.IgnoreUnmappedMapperConfig;
import com.example.bankcards.dto.account.AccountDto;
import com.example.bankcards.entity.account.Account;
import com.example.bankcards.service.AccountService;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    config = IgnoreUnmappedMapperConfig.class,
    uses = AccountService.class
)
public interface AccountMapper {
    @Mapping(target = "userId", source = "user.id")
    AccountDto toDto(Account account);

    @Mapping(target = "user", source = "userId")
    Account toEntity(AccountDto accountDto);
}
