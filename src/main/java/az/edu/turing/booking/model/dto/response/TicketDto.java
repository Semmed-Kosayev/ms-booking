package az.edu.turing.booking.model.dto.response;

import az.edu.turing.booking.model.enums.ClassType;

public record TicketDto(
        Long id,
        ClassType type,
        Integer price,
        Long flightId
) {
}
