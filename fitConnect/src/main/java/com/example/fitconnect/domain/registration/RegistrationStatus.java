package com.example.fitconnect.domain.registration;

public enum RegistrationStatus {
    APPLIED("신청됨"),
    APPROVED("승인됨"),
    REJECTED("거부됨"),
    CANCELED("취소됨");

    private final String description;

    RegistrationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}