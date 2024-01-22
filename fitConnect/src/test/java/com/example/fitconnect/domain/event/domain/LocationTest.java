package com.example.fitconnect.domain.event.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.fitconnect.config.exception.BusinessException;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class LocationTest {

    @ParameterizedTest
    @MethodSource("provideValidLocations")
    void createLocation_Success(City city, String address) {
        Location location = new Location(city, address);

        assertThat(location).isNotNull();
        assertThat(location.getCity()).isEqualTo(city);
        assertThat(location.getAddress()).isEqualTo(address);
    }

    private static Stream<Arguments> provideValidLocations() {
        return Stream.of(
                Arguments.of(City.SEOUL, "강남구 공원"),
                Arguments.of(City.BUSAN, "해운대구 떙땡아파트 앞")
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidLocations")
    void createLocation_Failure(City city, String address) {
        assertThatThrownBy(() -> new Location(city, address))
                .isInstanceOf(BusinessException.class);
    }

    private static Stream<Arguments> provideInvalidLocations() {
        return Stream.of(
                Arguments.of(City.SEOUL, null),
                Arguments.of(City.SEOUL, ""),
                Arguments.of(City.SEOUL, "  ")
        );
    }
}