package az.edu.turing.booking.model.dto.response;

import az.edu.turing.booking.model.enums.Nationality;
import az.edu.turing.booking.model.enums.Role;
import az.edu.turing.booking.model.enums.UserStatus;

import java.time.LocalDate;

public record UserDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        LocalDate dateOfBirth,
        Nationality nationality,
        Role role,
        UserStatus status
) {
}
