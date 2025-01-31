package az.edu.turing.booking.domain.repository;

import az.edu.turing.booking.domain.entity.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;

public interface FlightRepository extends JpaRepository<FlightEntity, Long>, JpaSpecificationExecutor<FlightEntity> {

    boolean existsByDepartureTimeAndDepartureAirportAndArrivalAirportAndAirlineName
            (LocalDateTime departureTime, String departureAirport, String arrivalAirport, String airlineName);
}
