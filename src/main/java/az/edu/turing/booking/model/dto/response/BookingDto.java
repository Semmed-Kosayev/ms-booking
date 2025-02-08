package az.edu.turing.booking.model.dto.response;

import az.edu.turing.booking.model.enums.BookingStatus;
import az.edu.turing.booking.model.enums.City;
import az.edu.turing.booking.model.enums.ClassType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BookingDto(
        Long id,
        String passengerFullName,
        String airlineName,
        City departureCity,
        City arrivalCity,
        String departureAirport,
        String arrivalAirport,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime,
        String duration,
        String seatNumber,
        ClassType classType,
        BookingStatus status
) {
}
