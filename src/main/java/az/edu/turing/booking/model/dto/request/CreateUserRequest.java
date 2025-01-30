package az.edu.turing.booking.model.dto.request;

import az.edu.turing.booking.model.enums.Nationality;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateUserRequest(
        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank
        String email,

        @NotBlank
        String phoneNumber,

        @NotNull
        LocalDate dateOfBirth,

        @NotNull
        Nationality nationality
) {
}
