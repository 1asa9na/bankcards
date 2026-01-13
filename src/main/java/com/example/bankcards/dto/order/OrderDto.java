package com.example.bankcards.dto.order;

import java.math.BigDecimal;
import java.util.UUID;

import com.example.bankcards.dto.validation.OnCreate;
import com.example.bankcards.dto.validation.OnUpdate;
import com.example.bankcards.entity.order.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderDto {

    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private UUID id;

    @NotNull(message = "Source account must be not null.", groups = {OnUpdate.class, OnCreate.class})
    private Long srcAccountId;

    @NotNull(message = "Destination account must be not null.", groups = { OnUpdate.class, OnCreate.class })
    private Long destAccountId;

    @NotNull(message = "Amount must be not null.", groups = { OnUpdate.class, OnCreate.class })
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OrderStatus status;
}
