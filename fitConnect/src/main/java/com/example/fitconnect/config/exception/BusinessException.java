package com.example.fitconnect.config.exception;

import com.example.fitconnect.config.error.ErrorMessages;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BusinessException extends RuntimeException {

    private final ErrorMessages errorMessages;
}
