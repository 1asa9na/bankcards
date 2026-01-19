package com.example.bankcards.dto.card;

import java.time.YearMonth;

import org.hibernate.validator.constraints.Length;

import com.example.bankcards.dto.validation.OnCreate;
import com.example.bankcards.dto.validation.OnUpdate;
import com.example.bankcards.entity.card.CardStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CardDto {

    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;

    private Long accountId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotNull(message = "PAN mask must be not null.", groups = {OnUpdate.class, OnCreate.class})
    private String panMask;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "PAN must not be null.", groups = OnCreate.class)
    @NotBlank(message = "PAN must not be empty.", groups = OnCreate.class)
    @Length(min = 13, max = 19, message = "PAN must contain 13 - 19 numbers", groups = OnCreate.class)
    @Pattern(regexp = "^[0-9 ]+$", message = "PAN must contain only numbers", groups = OnCreate.class)
    private String pan;

    @NotNull(message = "Expiration date must be not null.", groups = {OnUpdate.class, OnCreate.class})
    @JsonFormat(pattern = "yyyy-MM")
    private YearMonth expirationDate;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CardStatus status;

}
