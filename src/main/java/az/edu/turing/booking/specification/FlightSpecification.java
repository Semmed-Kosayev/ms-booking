package az.edu.turing.booking.specification;

import az.edu.turing.booking.domain.entity.FlightDetailsEntity;
import az.edu.turing.booking.domain.entity.FlightEntity;
import az.edu.turing.booking.domain.entity.Ticket;
import az.edu.turing.booking.model.dto.request.FlightFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

public class FlightSpecification {

    public static Specification<FlightEntity> filterFlights(FlightFilter filter) {
        return (Root<FlightEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Join<FlightEntity, FlightDetailsEntity> flightDetails = root.join("flightDetails", JoinType.LEFT);
            Join<FlightEntity, Ticket> tickets = root.join("tickets", JoinType.LEFT);
            Predicate predicate = cb.conjunction(); // `AND` condition

            if (!ObjectUtils.isEmpty(filter.departureCity())) {
                predicate = cb.and(predicate, cb.equal(root.get("departureCity"), filter.departureCity()));
            }
            if (!ObjectUtils.isEmpty(filter.arrivalCity())) {
                predicate = cb.and(predicate, cb.equal(root.get("arrivalCity"), filter.arrivalCity()));
            }
            if (!ObjectUtils.isEmpty(filter.departureDate())) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("departureDate"), filter.departureDate()));
            }
            if (!ObjectUtils.isEmpty(filter.ticketCount())) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(flightDetails.get("availableSeats"), filter.ticketCount()));
            }
            if (!ObjectUtils.isEmpty(filter.classType()) ) {
                predicate = cb.and(predicate, cb.equal(tickets.get("type"), filter.classType()));
            }
            if (!ObjectUtils.isEmpty(filter.maxPrice()) ) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(tickets.get("price"), filter.maxPrice()));
            }

            return predicate;
        };
    }
}
