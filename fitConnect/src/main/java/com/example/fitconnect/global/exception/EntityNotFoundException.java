package com.example.fitconnect.global.exception;

import com.example.fitconnect.global.error.ErrorMessages;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class EntityNotFoundException extends RuntimeException {

    private final ErrorMessages errorMessages;
}
