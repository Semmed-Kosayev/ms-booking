package az.edu.turing.booking.model.dto.request;

import az.edu.turing.booking.model.enums.ClassType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public final class CreateTicketRequest{
        @NotNull
        private ClassType type;
        @Min(1) @NotNull
        private Integer price;
        @Min(1) @NotNull
        private Long flightId;
}
