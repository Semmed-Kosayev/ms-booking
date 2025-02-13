package az.edu.turing.booking.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class FlightDto {
    private Long id;
    private String airlineName;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String departureAirport;
    private String arrivalAirport;
    private String departureCity;
    private String arrivalCity;
    private String aircraftModel;
    private String departureTerminal;
    private String arrivalTerminal;
    private Integer gateNumber;
    private Integer maxBaggageWeight;
    private Boolean isWifiAvailable;
    private Integer availableSeats;
    private Integer maxSeats;
    private String flightStatus;
}
