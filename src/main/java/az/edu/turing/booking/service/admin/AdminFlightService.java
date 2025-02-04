package az.edu.turing.booking.service.admin;

import az.edu.turing.booking.domain.entity.FlightEntity;
import az.edu.turing.booking.domain.repository.FlightRepository;
import az.edu.turing.booking.domain.repository.UserRepository;
import az.edu.turing.booking.exception.AlreadyExistsException;
import az.edu.turing.booking.exception.InvalidFlightDateException;
import az.edu.turing.booking.exception.NotFoundException;
import az.edu.turing.booking.exception.UnauthorizedAccessException;
import az.edu.turing.booking.mapper.FlightMapper;
import az.edu.turing.booking.model.dto.request.CreateFlightRequest;
import az.edu.turing.booking.model.dto.request.UpdateFlightRequest;
import az.edu.turing.booking.model.dto.response.FlightDto;
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

    private final FlightRepository flightRepository;
    private final UserRepository userRepository;
    private final FlightMapper mapper;

    @Transactional
    public UpdateFlightResponse updateFlight(long flightId, UpdateFlightRequest updateFlightRequest) {
        FlightEntity existingFlight = flightRepository.findById(flightId)
                .orElseThrow(() -> new NotFoundException("Flight with specified id not found"));

        checkAdminExistence(updateFlightRequest.adminId());

        FlightEntity updatedFlight = mapper.updateFlightEntityFromRequest(existingFlight, updateFlightRequest);

        return mapper.toFlightResponseDto(flightRepository.save(updatedFlight));
    }

    @Transactional
    public void deleteById(Long flightId, Long adminId) {
        checkAdminExistence(adminId);

        FlightEntity flightEntity = flightRepository.findById(flightId)
                .orElseThrow(() -> new NotFoundException("Flight not found"));

        flightEntity.getFlightDetail().setStatus(FlightStatus.CANCELLED);

        flightRepository.save(flightEntity);
    }

    @Transactional
    public FlightDto createFlight(CreateFlightRequest createFlightRequest, Long adminId) {
        checkAdminExistence(adminId);

        if (createFlightRequest.getDepartureTime().isBefore(LocalDateTime.now())) {
            throw new InvalidFlightDateException("Flight departure time cannot be in the past.");
        }
        boolean flightExists = flightRepository.existsByDepartureTimeAndDepartureAirportAndArrivalAirportAndAirlineName(
                createFlightRequest.getDepartureTime(),
                createFlightRequest.getDepartureAirport(),
                createFlightRequest.getArrivalAirport(),
                createFlightRequest.getAirlineName()
        );
        if (flightExists) {
            throw new AlreadyExistsException("A flight with the same details already exists.");
        }

        FlightEntity savedFlight = flightRepository.save(mapper.toEntity(createFlightRequest));

        return mapper.toFlightDto(savedFlight);
    }

    private void checkAdminExistence(Long adminId) {
        if (!userRepository.existsByIdAndRoleAdmin(adminId)) {
            throw new UnauthorizedAccessException("Admin with specified admin id not found");
        }
    }
}
