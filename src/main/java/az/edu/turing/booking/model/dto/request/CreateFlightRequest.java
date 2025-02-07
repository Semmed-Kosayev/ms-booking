package az.edu.turing.booking.model.dto.request;

import az.edu.turing.booking.model.enums.AircraftModel;
import az.edu.turing.booking.model.enums.City;
import az.edu.turing.booking.model.enums.FlightStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateFlightRequest {

    @NotBlank
    private String airlineName;
    @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime departureTime;
    @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Future
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
    private String departureTerminal;
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
    @Min(1)
    private Integer maxSeats;
    @NotNull
    private FlightStatus flightStatus;
}
