package az.edu.turing.booking.domain.entity;

import az.edu.turing.booking.model.enums.AircraftModel;
import az.edu.turing.booking.model.enums.FlightStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "flight_details")
public class FlightDetailEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "aircraft_model", nullable = false)
    private AircraftModel aircraftModel;

    @Column(name = "departure_terminal", nullable = false)
    private String departureTerminal;

    @Column(name = "arrival_terminal", nullable = false)
    private String arrivalTerminal;

    @Column(name = "gate_number", nullable = false)
    private Integer gateNumber;

    @Column(name = "max_baggage_weight", nullable = false)
    private Integer maxBaggageWeight;

    @Column(name = "is_wifi_available", nullable = false)
    private Boolean isWifiAvailable;

    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;

    @Column(name = "max_seats", nullable = false)
    private Integer maxSeats;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FlightStatus status;

    @OneToOne(mappedBy = "flightDetail", cascade = CascadeType.ALL)
    private FlightEntity flight;

    public Integer decreaseAvailableSeats() {
        availableSeats = availableSeats - 1;
        return availableSeats;
    }

    public Integer increaseAvailableSeats() {
        availableSeats = availableSeats + 1;
        return availableSeats;
    }
}
