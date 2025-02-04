package az.edu.turing.booking.service.admin;

import az.edu.turing.booking.domain.entity.FlightEntity;
import az.edu.turing.booking.domain.entity.Ticket;
import az.edu.turing.booking.domain.repository.FlightRepository;
import az.edu.turing.booking.domain.repository.TicketRepository;
import az.edu.turing.booking.domain.repository.UserRepository;
import az.edu.turing.booking.exception.NotFoundException;
import az.edu.turing.booking.exception.UnauthorizedAccessException;
import az.edu.turing.booking.mapper.TicketMapper;
import az.edu.turing.booking.model.dto.request.CreateTicketRequest;
import az.edu.turing.booking.model.dto.response.TicketDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminTicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final FlightRepository flightRepository;
    private final TicketMapper mapper;

    @Transactional
    public TicketDto create(CreateTicketRequest createTicketRequest, Long adminId) {
        checkAdminExistence(adminId);

        FlightEntity flight = flightRepository.findById(createTicketRequest.getFlightId())
                .orElseThrow(() -> new NotFoundException("Flight not found"));

        Ticket ticket = mapper.toTicket(createTicketRequest);
        ticket.setFlight(flight);

        return mapper.toTicketDto(ticketRepository.save(ticket));
    }

    private void checkAdminExistence(Long adminId) {
        if (!userRepository.existsByIdAndRoleAdmin(adminId)) {
            throw new UnauthorizedAccessException("Admin with specified admin id not found");
        }
    }
}
