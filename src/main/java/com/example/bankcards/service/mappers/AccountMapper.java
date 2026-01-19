package com.example.bankcards.service.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import com.example.bankcards.config.mapper.IgnoreUnmappedMapperConfig;
import com.example.bankcards.dto.account.AccountDto;
import com.example.bankcards.entity.account.Account;
import com.example.bankcards.service.UserService;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    config = IgnoreUnmappedMapperConfig.class,
    uses = UserService.class
)
public abstract class AccountMapper {

    @Mapping(target = "userId", source = "user.id")
    public abstract AccountDto toDto(Account account);

    public abstract List<AccountDto> toDto(List<Account> accounts);

    @Mapping(target = "user", source = "userId")
    public abstract Account toEntity(AccountDto accountDto);

    @Mapping(target = "user", source = "userId")
    public abstract Account toEntity(AccountDto accountDto, @MappingTarget Account account);
}
