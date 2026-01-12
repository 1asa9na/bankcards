package com.example.bankcards.repository.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.example.bankcards.dto.user.UserDto;
import com.example.bankcards.entity.user.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto dto);
}
