package com.example.footballmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse{
    private int status;
    private String error;
    private String message;
    private String stackTrace;
    private LocalDateTime timestamp;
}
