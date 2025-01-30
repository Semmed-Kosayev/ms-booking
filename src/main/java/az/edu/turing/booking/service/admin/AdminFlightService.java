package az.edu.turing.booking.service.admin;

import az.edu.turing.booking.domain.entity.FlightDetailsEntity;
import az.edu.turing.booking.domain.entity.FlightEntity;
import az.edu.turing.booking.domain.repository.FlightRepository;
import az.edu.turing.booking.exception.AlreadyExistsException;
import az.edu.turing.booking.exception.InvalidException;
import az.edu.turing.booking.exception.InvalidFlightDateException;
import az.edu.turing.booking.exception.NotFoundException;
import az.edu.turing.booking.mapper.FlightMapper;
import az.edu.turing.booking.model.dto.FlightDto;
import az.edu.turing.booking.model.dto.request.CreateFlightRequest;
import az.edu.turing.booking.model.dto.request.UpdateFlightRequest;
import az.edu.turing.booking.model.dto.response.UpdateFlightResponse;
import az.edu.turing.booking.model.enums.FlightStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminFlightService {

    private final FlightRepository repository;
    private final FlightMapper mapper;

    @Transactional
    public UpdateFlightResponse updateFlight(long id, UpdateFlightRequest updateFlightRequest) {
        FlightEntity existingFlight = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Flight with specified id not found"));

        existingFlight = updateFlightEntity(existingFlight, updateFlightRequest);
        FlightEntity updatedFlight = repository.save(existingFlight);

        return mapper.toFlightResponseDto(updatedFlight);
    }

    private FlightEntity updateFlightEntity(FlightEntity existingFlight, UpdateFlightRequest updateFlightRequest) {
        existingFlight.setUpdatedBy(updateFlightRequest.adminId());
        existingFlight.setUpdatedAt(LocalDateTime.now());

        existingFlight.setAirlineName(updateFlightRequest.airlineName());
        existingFlight.setDepartureTime(updateFlightRequest.departureTime());
        existingFlight.setArrivalTime(updateFlightRequest.arrivalTime());
        existingFlight.setDepartureAirport(updateFlightRequest.departureAirport());
        existingFlight.setArrivalAirport(updateFlightRequest.arrivalAirport());
        existingFlight.setDepartureCity(updateFlightRequest.departureCity());
        existingFlight.setArrivalCity(updateFlightRequest.arrivalCity());

        FlightDetailsEntity flightDetails = existingFlight.getFlightDetails();
        if (flightDetails == null) {
            flightDetails = new FlightDetailsEntity();
        }
        flightDetails.setAircraftModel(updateFlightRequest.aircraftModel());
        flightDetails.setDepartureTerminal(updateFlightRequest.departureTerminal());
        flightDetails.setArrivalTerminal(updateFlightRequest.arrivalTerminal());
        flightDetails.setGateNumber(updateFlightRequest.gateNumber());
        flightDetails.setMaxBaggageWeight(updateFlightRequest.maxBaggageWeight());
        flightDetails.setIsWifiAvailable(updateFlightRequest.isWifiAvailable());
        flightDetails.setAvailableSeats(updateFlightRequest.availableSeats());
        flightDetails.setMaxSeats(updateFlightRequest.maxSeats());
        flightDetails.setStatus(updateFlightRequest.flightStatus());

        existingFlight.setFlightDetails(flightDetails);

        return existingFlight;
    }

    @Transactional
    public void deleteById(long id) {

        FlightEntity flightEntity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Flight not found"));

        flightEntity.getFlightDetails().setStatus(FlightStatus.CANCELLED);

        repository.save(flightEntity);

    }

    @Transactional
    public FlightDto createFlight(CreateFlightRequest createFlightRequest) {
        if (createFlightRequest.getDepartureTime().isBefore(LocalDateTime.now())) {
            throw new InvalidFlightDateException("Flight departure time cannot be in the past.");
        }
        boolean flightExists = repository.existsByDepartureTimeAndDepartureAirportAndArrivalAirportAndAirlineName(
                createFlightRequest.getDepartureTime(),
                createFlightRequest.getDepartureAirport(),
                createFlightRequest.getArrivalAirport(),
                createFlightRequest.getAirlineName()
        );
        if (flightExists) {
            throw new AlreadyExistsException("A flight with the same details already exists.");
        }
        if (createFlightRequest.getMaxSeats() <= 0) {
            throw new InvalidException("Seat capacity must be greater than zero.");
        }
        FlightEntity flightEntity = mapper.toEntity(createFlightRequest);

        FlightEntity savedFlight = repository.save(flightEntity);
        return mapper.toFlightDto(savedFlight);
    }
}
