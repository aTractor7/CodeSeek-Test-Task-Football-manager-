package com.example.footballmanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PlayerCreateDto {

    @NotBlank(message = "Name can't be empty")
    @Size(min = 1, max = 30, message = "Player name should be between 1 and 30 characters")
    private String name;

    @NotNull(message = "Debut date can't be empty")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    private LocalDate debutDate;

    @NotNull(message = "Age can't be empty")
    @Min(value = 15, message = "Player should be older than 15 years")
    private Integer age;

    @NotNull(message = "Player should be owned by some team")
    @Min(value = 0, message = "Id can not be less than 0")
    private Long teamId;
}
