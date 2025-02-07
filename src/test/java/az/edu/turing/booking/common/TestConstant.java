package az.edu.turing.booking.common;

import az.edu.turing.booking.model.dto.request.CreateFlightRequest;
import az.edu.turing.booking.model.dto.request.UpdateFlightRequest;
import az.edu.turing.booking.model.dto.response.FlightDto;
import az.edu.turing.booking.model.dto.response.UpdateFlightResponse;
import az.edu.turing.booking.model.enums.AircraftModel;
import az.edu.turing.booking.model.enums.City;
import az.edu.turing.booking.model.enums.FlightStatus;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public interface TestConstant {

    Long FLIGHT_ID = 1L;
    Long ADMIN_ID = 1L;
    Long NOT_EXIST_FLIGHT_ID = 99L;

    FlightDto FLIGHT_DTO = getFlightDto();

    List<FlightDto> LIST_FLIGHT_DTO = Arrays.asList(FLIGHT_DTO);

    UpdateFlightRequest UPDATE_FLIGHT_REQUEST = UpdateFlightRequest.builder()
            .airlineName("Azerbaijan Airlines")
            .departureTime(LocalDateTime.of(2025, 3, 18, 16, 0, 0))
            .arrivalTime(LocalDateTime.of(2025, 3, 18, 18, 0, 0))
            .departureAirport("JFK")
            .arrivalAirport("LHR")
            .departureCity(City.BAKU)
            .arrivalCity(City.TOKYO)
            .aircraftModel(AircraftModel.AIRBUS_A320)
            .departureTerminal("T3")
            .arrivalTerminal("T4")
            .gateNumber(13)
            .maxBaggageWeight(30)
            .isWifiAvailable(true)
            .availableSeats(140)
            .maxSeats(150)
            .flightStatus(FlightStatus.AVAILABLE)
            .build();

    UpdateFlightResponse UPDATE_FLIGHT_RESPONSE = UpdateFlightResponse.builder()
            .airlineName("Azerbaijan Airlines")
            .departureTime(LocalDateTime.of(2025, 3, 18, 16, 0, 0))
            .arrivalTime(LocalDateTime.of(2025, 3, 18, 18, 0, 0))
            .departureAirport("JFK")
            .arrivalAirport("LHR")
            .departureCity(City.BAKU)
            .arrivalCity(City.TOKYO)
            .aircraftModel(AircraftModel.AIRBUS_A320)
            .departureTerminal("T3")
            .arrivalTerminal("T4")
            .gateNumber(13)
            .maxBaggageWeight(30)
            .isWifiAvailable(true)
            .availableSeats(140)
            .maxSeats(150)
            .flightStatus(FlightStatus.AVAILABLE)
            .build();

    CreateFlightRequest CREATE_FLIGHT_REQUEST = CreateFlightRequest.builder()
            .airlineName("Azerbaijan Airlines")
            .departureTime(LocalDateTime.of(2025, 3, 18, 16, 0, 0))
            .arrivalTime(LocalDateTime.of(2025, 3, 18, 18, 0, 0))
            .departureAirport("JFK")
            .arrivalAirport("LHR")
            .departureCity(City.BAKU)
            .arrivalCity(City.TOKYO)
            .aircraftModel(AircraftModel.EMBRAER_E175)
            .departureTerminal("T3")
            .arrivalTerminal("T4")
            .gateNumber(13)
            .maxBaggageWeight(30)
            .isWifiAvailable(true)
            .availableSeats(140)
            .maxSeats(150)
            .flightStatus(FlightStatus.AVAILABLE)
            .build();

    FlightDto CREATE_FLIGHT_DTO = FlightDto.builder()
            .id(1L)
            .airlineName("Azerbaijan Airlines")
            .departureTime(LocalDateTime.of(2025, 3, 18, 16, 0, 0))
            .arrivalTime(LocalDateTime.of(2025, 3, 18, 18, 0, 0))
            .departureAirport("JFK")
            .arrivalAirport("LHR")
            .departureCity("BAKU")
            .arrivalCity("TOKYO")
            .aircraftModel("EMBRAER_E175")
            .departureTerminal("T3")
            .arrivalTerminal("T4")
            .gateNumber(13)
            .maxBaggageWeight(30)
            .isWifiAvailable(true)
            .availableSeats(140)
            .maxSeats(150)
            .flightStatus("AVAILABLE")
            .build();

    static FlightDto getFlightDto() {
        return FlightDto.builder()
                .id(1L)
                .airlineName("Example Airlines")
                .departureTime(LocalDateTime.of(2024, 6, 1, 8, 0, 0))
                .arrivalTime(LocalDateTime.of(2024, 6, 1, 12, 0, 0))
                .departureAirport("JFK")
                .arrivalAirport("LHR")
                .departureCity("NEW_YORK")
                .arrivalCity("LONDON")
                .aircraftModel("BOEING_737")
                .departureTerminal("T1")
                .arrivalTerminal("T2")
                .gateNumber(5)
                .maxBaggageWeight(30)
                .isWifiAvailable(true)
                .availableSeats(180)
                .maxSeats(200)
                .flightStatus("AVAILABLE")
                .build();
    }
}
