package com.example.footballmanager.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TeamDto {

    private Long id;

    private String name;

    private Double commission;

    private BigDecimal balance;

    private List<PlayerDto> players;
}
