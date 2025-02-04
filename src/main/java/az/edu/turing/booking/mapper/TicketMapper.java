package az.edu.turing.booking.mapper;

import az.edu.turing.booking.domain.entity.Ticket;
import az.edu.turing.booking.model.dto.request.CreateTicketRequest;
import az.edu.turing.booking.model.dto.response.TicketDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    TicketMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    @Mapping(target = "flightId", source = "ticket.flight.id")
    TicketDto toTicketDto(Ticket ticket);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "flight", ignore = true)
    Ticket toTicket(CreateTicketRequest ticketDto);
}
