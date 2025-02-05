package az.edu.turing.booking.model.dto.request;

import az.edu.turing.booking.model.enums.BookingStatus;
import az.edu.turing.booking.model.enums.ClassType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookingUpdateRequest(
        @NotBlank
        String seatNumber,

        @NotNull
        ClassType classType,

        @NotNull
        BookingStatus status,

        @Min(1)
        @NotNull
        Long flightId,

        @Min(1)
        @NotNull
        Long passengerId

) {
}
