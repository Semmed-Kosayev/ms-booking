package az.edu.turing.booking.domain.repository;

import az.edu.turing.booking.domain.entity.FlightEntity;
import az.edu.turing.booking.model.enums.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<FlightEntity, Long> {
    @Query(value = """
                SELECT f FROM FlightEntity f 
                JOIN f.flightDetails fd
                WHERE f.arrivalCity = :destination
                AND DATE (f.departureTime) = :date
                AND fd.availableSeats >= :passengerCount
            """)
    List<FlightEntity> searchFlights(@Param("destination") City destination,
                                     @Param("date") LocalDate date,
                                     @Param("passengerCount") int passengerCount);

    boolean existsByDepartureTimeAndDepartureAirportAndArrivalAirportAndAirlineName
            (LocalDateTime departureTime, String departureAirport, String arrivalAirport, String airlineName);
}
