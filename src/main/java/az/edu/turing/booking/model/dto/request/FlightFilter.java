package az.edu.turing.booking.model.dto.request;

import az.edu.turing.booking.model.enums.City;
import az.edu.turing.booking.model.enums.ClassType;
import jakarta.validation.constraints.Min;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@ParameterObject
public record FlightFilter(
        City departureCity,

        City arrivalCity,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate departureDate,

        @Min(1)
        Integer ticketCount,

        ClassType classType,

        @Min(0)
        Double maxPrice
) {
}
