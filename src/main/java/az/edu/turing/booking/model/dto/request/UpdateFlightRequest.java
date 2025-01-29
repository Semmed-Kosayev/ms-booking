package az.edu.turing.booking.model.dto.request;

import az.edu.turing.booking.model.enums.AircraftModel;
import az.edu.turing.booking.model.enums.City;
import az.edu.turing.booking.model.enums.FlightStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UpdateFlightRequest(

        @Min(1) long adminId,

        @NotBlank String airlineName,
        @NotNull LocalDateTime departureTime,
        @NotNull LocalDateTime arrivalTime,
        @NotBlank String departureAirport,
        @NotBlank String arrivalAirport,
        @NotNull City departureCity,
        @NotNull City arrivalCity,

        @NotNull AircraftModel aircraftModel,
        @NotBlank String departureTerminal,
        @NotBlank String arrivalTerminal,
        @NotNull Integer gateNumber,
        @NotNull Integer maxBaggageWeight,
        @NotNull Boolean isWifiAvailable,
        @NotNull Integer availableSeats,
        @NotNull Integer maxSeats,
        @NotNull FlightStatus flightStatus
) {
}
