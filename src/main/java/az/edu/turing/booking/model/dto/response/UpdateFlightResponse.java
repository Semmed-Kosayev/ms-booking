package az.edu.turing.booking.model.dto.response;

import az.edu.turing.booking.model.enums.AircraftModel;
import az.edu.turing.booking.model.enums.City;
import az.edu.turing.booking.model.enums.FlightStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
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
