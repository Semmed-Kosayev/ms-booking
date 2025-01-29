package az.edu.turing.booking.service;

import az.edu.turing.booking.domain.repository.FlightRepository;
import az.edu.turing.booking.mapper.FlightMapper;
import az.edu.turing.booking.model.dto.FlightDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    public List<FlightDto> getAllFlight() {
        return flightRepository.findAll().stream().map(flightMapper::toFlightDto).collect(Collectors.toList());
    }
}
