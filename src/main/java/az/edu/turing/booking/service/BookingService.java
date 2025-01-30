package az.edu.turing.booking.service;

import az.edu.turing.booking.domain.entity.BookingEntity;
import az.edu.turing.booking.domain.entity.FlightEntity;
import az.edu.turing.booking.domain.entity.UserEntity;
import az.edu.turing.booking.domain.repository.BookingRepository;
import az.edu.turing.booking.domain.repository.FlightRepository;
import az.edu.turing.booking.domain.repository.UserRepository;
import az.edu.turing.booking.exception.NotFoundException;
import az.edu.turing.booking.mapper.BookingMapper;
import az.edu.turing.booking.model.dto.BookingDto;
import az.edu.turing.booking.model.dto.request.CreateBookingRequest;
import az.edu.turing.booking.model.dto.response.ResponseBookingDto;
import az.edu.turing.booking.model.enums.BookingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    public ResponseBookingDto getById(Long id) {
        BookingEntity bookingById = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking with specified id not found"));

        return bookingMapper.toResponseBookingDto(bookingById);
    }

    public Page<BookingDto> getAllByPassengerId(Long passengerId, Pageable pageable) {
        return bookingRepository.findAllByPassengerId(passengerId, pageable)
                .map(bookingMapper::toBookingDto);
    }

    @Transactional
    public void deleteById(long id) {
        BookingEntity bookingById = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking with specified id not found"));
        bookingById.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(bookingById);
    }

    @Transactional
    public ResponseBookingDto create(CreateBookingRequest request) {
        FlightEntity flightEntity = flightRepository.findById(request.flightId())
                .orElseThrow(() -> new NotFoundException("Flight with specified id not found"));
        UserEntity userEntity = userRepository.findById(request.passengerId())
                .orElseThrow(() -> new NotFoundException("User with specified id not found"));

        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setFlight(flightEntity);
        bookingEntity.setPassenger(userEntity);
        bookingEntity.setStatus(BookingStatus.CONFIRMED);
        bookingEntity.setClassType(request.classType());
        bookingEntity.setSeatNumber(request.seatNumber());
        bookingEntity.setFlightDate(flightEntity.getDepartureTime());
        bookingEntity.setCreatedAt(LocalDateTime.now());
        bookingEntity.setUpdatedAt(LocalDateTime.now());
        bookingEntity.setCreatedBy(request.passengerId());
        bookingEntity.setUpdatedBy(request.passengerId());
        BookingEntity savedBooking = bookingRepository.save(bookingEntity);
        return bookingMapper.toResponseBookingDto(savedBooking);
    }
}
