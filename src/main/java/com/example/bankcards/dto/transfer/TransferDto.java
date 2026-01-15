package com.example.bankcards.dto.transfer;

import java.math.BigDecimal;

import com.example.bankcards.dto.validation.OnCreate;
import com.example.bankcards.entity.transfer.TransferStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransferDto {

    @NotNull(message = "Source account must be not null.", groups = { OnCreate.class})
    private Long srcCardId;

    @NotNull(message = "Destination account must be not null.", groups = { OnCreate.class })
    private Long destCardId;

    @NotNull(message = "Amount must be not null.", groups = { OnCreate.class })
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private TransferStatus status;
}
