package com.example.footballmanager.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TeamCreateDto {

    @NotBlank(message = "Name can't be empty")
    @Size(min = 2, max = 30, message = "Team name should be between 2 and 30 characters")
    private String name;

    @NotNull(message = "Commission can't be empty")
    @DecimalMin(value = "0.0", inclusive = true, message = "Commission can't be less than 0")
    @DecimalMax(value = "0.1", inclusive = true, message = "Commission can't be greater than 0.1 (10%)")
    private Double commission;

    private BigDecimal balance;

    private List<PlayerCreateDto> players;
}
