package com.example.bankcards.repository.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.example.bankcards.config.mapper.IgnoreUnmappedMapperConfig;
import com.example.bankcards.dto.order.OrderDto;
import com.example.bankcards.entity.order.Order;
import com.example.bankcards.service.OrderService;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    config = IgnoreUnmappedMapperConfig.class,
    uses = OrderService.class
)
public interface OrderMapper {
    
    @Mapping(target = "srcAccountId", source = "srcAccount.id")
    @Mapping(target = "destAccountId", source = "destAccount.id")
    OrderDto toDto(Order order);

    @Mapping(target = "srcAccount", source = "srcAccountId")
    @Mapping(target = "destAccount", source = "destAccountId")
    Order toEntity(OrderDto orderDto);
}
