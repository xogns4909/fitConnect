package com.example.fitconnect.domain.event.dto;

import com.example.fitconnect.domain.event.domain.City;
import com.example.fitconnect.domain.event.domain.Location;

public class LocationDto {
    private City city;
    private String address;

    public LocationDto(City city, String address) {
        this.city = city;
        this.address = address;
    }

    public Location toEntity() {
        return new Location(city, address);
    }
}
