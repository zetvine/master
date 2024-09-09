package ru.test.taskmanager.exceptions;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorApi {
    private String message;
    private String reason;
    private String status;
    private LocalDateTime timestamp;
}