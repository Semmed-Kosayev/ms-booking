package az.edu.turing.booking.model.dto;

import az.edu.turing.booking.model.enums.BookingStatus;
import az.edu.turing.booking.model.enums.ClassType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private Long id;
    private LocalDateTime flightDate;
    private String seatNumber;
    private ClassType classType;
    private BookingStatus status;
    private Long passengerId;
    private Long flightId;
}
