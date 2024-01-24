package com.example.fitconnect.config.exception;

import com.example.fitconnect.config.error.ErrorMessages;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class EntityNotFoundException extends RuntimeException {

    private final ErrorMessages errorMessages;
}
