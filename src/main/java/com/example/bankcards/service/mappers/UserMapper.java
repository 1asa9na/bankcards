package com.example.bankcards.service.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

import com.example.bankcards.config.mapper.IgnoreUnmappedMapperConfig;
import com.example.bankcards.dto.user.UserDto;
import com.example.bankcards.entity.user.User;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    config = IgnoreUnmappedMapperConfig.class
)
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto dto);
    User toEntity(UserDto dto, @MappingTarget User user);
}
