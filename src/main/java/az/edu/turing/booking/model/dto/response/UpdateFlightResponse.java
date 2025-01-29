package az.edu.turing.booking.model.dto.response;

import az.edu.turing.booking.model.enums.AircraftModel;
import az.edu.turing.booking.model.enums.City;
import az.edu.turing.booking.model.enums.FlightStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UpdateFlightResponse(

        String airlineName,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime,
        String departureAirport,
        String arrivalAirport,
        City departureCity,
        City arrivalCity,

        AircraftModel aircraftModel,
        String departureTerminal,
        String arrivalTerminal,
        Integer gateNumber,
        Integer maxBaggageWeight,
        Boolean isWifiAvailable,
        Integer availableSeats,
        Integer maxSeats,
        FlightStatus flightStatus
) {
}
