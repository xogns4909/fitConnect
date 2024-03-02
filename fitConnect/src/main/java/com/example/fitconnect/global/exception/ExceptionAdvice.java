package com.example.fitconnect.global.exception;

import com.example.fitconnect.global.error.ErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(BusinessException e) {
        ErrorMessages errorMessage = e.getErrorMessages();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
        ErrorMessages errorMessage = e.getErrorMessages();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage.getMessage());
    }
    
}