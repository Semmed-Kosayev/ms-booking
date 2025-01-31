package az.edu.turing.booking.model.dto.request;

import az.edu.turing.booking.model.enums.AircraftModel;
import az.edu.turing.booking.model.enums.City;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFlightRequest {

    @NotBlank
    private String airlineName;
    @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime departureTime;
    @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime arrivalTime;
    @NotBlank
    private String departureAirport;
    @NotBlank
    private String arrivalAirport;
    @NotNull
    private City departureCity;
    @NotNull
    private City arrivalCity;
    @NotNull
    private AircraftModel aircraftModel;
    @NotNull
    private AircraftModel departureTerminal;
    @NotBlank
    private String arrivalTerminal;
    @NotNull
    private Integer gateNumber;
    @NotNull
    private Integer maxBaggageWeight;
    @NotNull
    private Boolean isWifiAvailable;
    @NotNull
    private Integer availableSeats;
    @NotNull
    private Integer maxSeats;
    @NotBlank
    private String flightStatus;
}
