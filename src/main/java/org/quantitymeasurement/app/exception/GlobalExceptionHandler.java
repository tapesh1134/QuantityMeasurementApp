package org.quantitymeasurement.app.exception;

import org.quantitymeasurement.app.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto<>(false, ex.getMessage()));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatusException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(new ApiResponseDto<>(false,ex.getReason()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDto<>(false,e.getMessage()));
    }

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
