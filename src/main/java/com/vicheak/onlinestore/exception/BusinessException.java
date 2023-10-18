package com.vicheak.onlinestore.exception;

import com.vicheak.onlinestore.base.BaseError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class BusinessException {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleBusinessEx(ResponseStatusException ex) {
        var baseError = BaseError.builder()
                .message("Something went wrong!")
                .code(7000)
                .status(false)
                .timestamp(LocalDateTime.now())
                .errors(ex.getReason())
                .build();

        return new ResponseEntity<>(baseError, ex.getStatusCode());
    }

}
