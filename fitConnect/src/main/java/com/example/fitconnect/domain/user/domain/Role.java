package com.example.fitconnect.domain.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("운영자"),
    MEMBER("회원");

    private final String description;
}