package com.gym.fit_power.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TrainerNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTrainerNotFound(TrainerNotFoundException e) {
        return createErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(DuplicatedTrainerException.class)
    public ResponseEntity<Map<String, String>> handleDuplicatedTrainer(DuplicatedTrainerException e) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(TrainerUpdateException.class)
    public ResponseEntity<Map<String, String>> handleTrainerUpdate(TrainerUpdateException e) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    private ResponseEntity<Map<String, String>> createErrorResponse(HttpStatus status, String errorMessage) {
        return ResponseEntity.status(status).body(Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "error", errorMessage
        ));
    }
}
