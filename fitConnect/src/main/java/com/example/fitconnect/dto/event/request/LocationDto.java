package com.example.fitconnect.dto.event.request;

import com.example.fitconnect.domain.event.domain.City;
import com.example.fitconnect.domain.event.domain.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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
