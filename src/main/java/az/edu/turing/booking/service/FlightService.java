package az.edu.turing.booking.service;

import az.edu.turing.booking.domain.entity.FlightEntity;
import az.edu.turing.booking.domain.repository.FlightRepository;
import az.edu.turing.booking.mapper.FlightMapper;
import az.edu.turing.booking.model.dto.FlightDto;
import az.edu.turing.booking.model.enums.City;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    public Page<FlightDto> getAllFlight(Pageable pageable, City destination, LocalDate date, int ticketCount) {
        if (destination != null || date != null || ticketCount != 0) {
            return new PageImpl<>(flightRepository.searchFlights(destination, date, ticketCount).stream()
                    .map(flightMapper::toFlightDto).toList());
        }
        return flightRepository.findAll(pageable).map(flightMapper::toFlightDto);
    }
}
