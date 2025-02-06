package az.edu.turing.booking.service.admin;

import az.edu.turing.booking.domain.entity.BookingEntity;
import az.edu.turing.booking.domain.entity.FlightEntity;
import az.edu.turing.booking.domain.entity.UserEntity;
import az.edu.turing.booking.domain.repository.BookingRepository;
import az.edu.turing.booking.domain.repository.FlightRepository;
import az.edu.turing.booking.domain.repository.UserRepository;
import az.edu.turing.booking.exception.NotFoundException;
import az.edu.turing.booking.exception.UnauthorizedAccessException;
import az.edu.turing.booking.mapper.BookingMapper;
import az.edu.turing.booking.model.dto.request.BookingUpdateRequest;
import az.edu.turing.booking.model.dto.response.BookingDto;
import az.edu.turing.booking.model.enums.BookingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminBookingService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;

    public List<BookingDto> getAll(Long adminId) {
        checkAdminExistence(adminId);
        return bookingMapper.toResponseBookingDtoList(bookingRepository.findAll());
    }

    @Transactional
    public BookingDto update(long id, BookingUpdateRequest updateRequest) {
        checkAdminExistence(updateRequest.adminId());
        FlightEntity flightEntity = flightRepository.findById(updateRequest.flightId())
                .orElseThrow(() -> new NotFoundException("Flight with specified id not found"));
        UserEntity userEntity = userRepository.findById(updateRequest.passengerId())
                .orElseThrow(() -> new NotFoundException("User with specified id not found"));
        BookingEntity bookingEntity = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking with specified id not found"));

        BookingEntity updatedBookingEntity =
                bookingMapper.updateBookingEntityFromRequest(bookingEntity, updateRequest, userEntity, flightEntity);

        BookingEntity save = bookingRepository.save(updatedBookingEntity);

        return bookingMapper.toBookingDto(save);
    }

    public BookingDto getById(Long bookingId, Long adminId) {
        checkAdminExistence(adminId);
        BookingEntity bookingEntity = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking with specified id not found"));
        return bookingMapper.toBookingDto(bookingRepository.save(bookingEntity));
    }

    @Transactional
    public void deleteById(Long bookingId, Long adminId) {
        checkAdminExistence(adminId);
        BookingEntity bookingEntity = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Booking with specified id not found"));

        FlightEntity flight = bookingEntity.getFlight();
        flight.getFlightDetail().increaseAvailableSeats();
        flightRepository.save(flight);

        bookingEntity.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(bookingEntity);
    }

    private void checkAdminExistence(Long adminId) {
        if (!userRepository.existsByIdAndRoleAdmin(adminId)) {
            throw new UnauthorizedAccessException("Admin with specified admin id not found");
        }
    }
}
