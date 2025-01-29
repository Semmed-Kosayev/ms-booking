package az.edu.turing.booking.service;

import az.edu.turing.booking.domain.repository.FlightRepository;
import az.edu.turing.booking.mapper.FlightMapper;
import az.edu.turing.booking.model.dto.FlightDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    public Page<FlightDto> getAllFlight(Pageable pageable) {
        return flightRepository.findAll(pageable).map(flightMapper::toFlightDto);
    }
}
