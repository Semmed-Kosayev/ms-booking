package az.edu.turing.booking.model.dto.request;

import az.edu.turing.booking.model.enums.Nationality;
import az.edu.turing.booking.model.enums.Role;
import az.edu.turing.booking.model.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserDto {
    @Min(1) @NotNull
    private Long adminId;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String phoneNumber;
    @NotNull
    private LocalDate dateOfBirth;
    @NotNull
    private Nationality nationality;
    @NotNull
    private Role role;
    @NotNull
    private UserStatus status;
}
