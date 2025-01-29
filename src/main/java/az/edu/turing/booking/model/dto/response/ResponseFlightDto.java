package az.edu.turing.booking.model.dto.response;

import az.edu.turing.booking.model.enums.City;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public record ResponseFlightDto(
        Long id,
        String airlineName,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime,
        String departureAirport,
        String arrivalAirport,
        City departureCity,
        City arrivalCity
        ) {
}
