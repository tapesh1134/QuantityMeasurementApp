package com.example.qmahistoryservice.exception;

import com.example.qmahistoryservice.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex){
        System.out.println(ex.getClass().getName());
        Throwable cause = ex.getCause();

        if (cause instanceof ResponseStatusException rse) {
            return ResponseEntity
                    .status(rse.getStatusCode())
                    .body(rse.getReason());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDto<>(false,ex.getMessage()));
    }
}
