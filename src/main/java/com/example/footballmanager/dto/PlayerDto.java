package com.example.footballmanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlayerDto {

    private Long id;

    private String name;

    private LocalDate debutDate;

    private Integer age;

    private Long teamId;
}
