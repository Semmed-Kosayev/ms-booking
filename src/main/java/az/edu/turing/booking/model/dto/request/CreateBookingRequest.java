package az.edu.turing.booking.model.dto.request;

import az.edu.turing.booking.model.enums.ClassType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record CreateBookingRequest(
        @Min(1)
        @NotNull
        Long passengerId,
        @Min(1)
        @NotNull
        Long flightId,
        @NotNull
        ClassType classType,
        @NotBlank
        String seatNumber
) {
}
