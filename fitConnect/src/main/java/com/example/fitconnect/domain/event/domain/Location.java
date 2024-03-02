package com.example.fitconnect.domain.event.domain;

import com.example.fitconnect.global.error.ErrorMessages;
import com.example.fitconnect.global.exception.BusinessException;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Embeddable
@Getter
public class Location {

    @Enumerated(EnumType.STRING)
    private City city;
    private String address;

    public Location(City city, String address) {
        validateAddress(address);
        this.city = city;
        this.address = address;
    }

    public Location() {

    }

    private void validateAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new BusinessException(ErrorMessages.ADDRESS_NULL_OR_EMPTY);
        }
    }
}