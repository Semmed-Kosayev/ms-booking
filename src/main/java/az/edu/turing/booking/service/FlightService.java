package az.edu.turing.booking.service;

import az.edu.turing.booking.domain.entity.FlightEntity;
import az.edu.turing.booking.domain.repository.FlightRepository;
import az.edu.turing.booking.exception.NotFoundException;
import az.edu.turing.booking.mapper.FlightMapper;
import az.edu.turing.booking.model.dto.request.FlightFilter;
import az.edu.turing.booking.model.dto.response.FlightDto;
import az.edu.turing.booking.specification.FlightSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    public Page<FlightDto> getAllFlight(Pageable pageable, FlightFilter filter) {
        Specification<FlightEntity> spec = FlightSpecification.filterFlights(filter);
        return flightRepository.findAll(spec, pageable).map(flightMapper::toFlightDto);
    }

    public FlightDto getById(Long id) {
        FlightEntity flightById = flightRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Flight with specified id not found"));
        return flightMapper.toFlightDto(flightById);
    }

}
