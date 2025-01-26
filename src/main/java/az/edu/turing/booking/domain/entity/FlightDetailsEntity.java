package az.edu.turing.booking.domain.entity;

import az.edu.turing.booking.model.enums.AircraftModel;
import az.edu.turing.booking.model.enums.FlightStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "flight_details")
public class FlightDetailsEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
}
