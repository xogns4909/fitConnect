package com.example.fitconnect.domain.event.domain;

public enum Category {
    SOCCER("축구"),
    BASKETBALL("농구"),
    BASEBALL("야구"),
    GOLF("골프"),
    FITNESS("헬스"),
    BOWLING("볼링"),
    BILLIARDS("당구");

    private final String name;

    Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}