package az.edu.turing.booking.domain.repository;

import az.edu.turing.booking.domain.entity.FlightEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, Long>, JpaSpecificationExecutor<FlightEntity> {

    @EntityGraph(attributePaths = {"flightDetail"})
    Page<FlightEntity> findAll(Specification<FlightEntity> spec, Pageable pageable);

    boolean existsByDepartureTimeAndDepartureAirportAndArrivalAirportAndAirlineName
            (LocalDateTime departureTime, String departureAirport, String arrivalAirport, String airlineName);
}
