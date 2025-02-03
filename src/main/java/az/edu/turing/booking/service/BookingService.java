package az.edu.turing.booking.service;

import az.edu.turing.booking.domain.entity.BookingEntity;
import az.edu.turing.booking.domain.entity.FlightDetailEntity;
import az.edu.turing.booking.domain.entity.FlightEntity;
import az.edu.turing.booking.domain.entity.UserEntity;
import az.edu.turing.booking.domain.repository.BookingRepository;
import az.edu.turing.booking.domain.repository.FlightRepository;
import az.edu.turing.booking.domain.repository.UserRepository;
import az.edu.turing.booking.exception.NotEnoughSeatsException;
import az.edu.turing.booking.exception.NotFoundException;
import az.edu.turing.booking.mapper.BookingMapper;
import az.edu.turing.booking.model.dto.request.CreateBookingRequest;
import az.edu.turing.booking.model.dto.response.BookingDto;
import az.edu.turing.booking.model.enums.BookingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    public BookingDto getById(Long id) {
        BookingEntity bookingById = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking with specified id not found"));

        return bookingMapper.toBookingDto(bookingById);
    }

    public Page<BookingDto> getAllByPassengerId(Long passengerId, Pageable pageable) {
        Page<BookingEntity> allByPassengerId = bookingRepository.findAllByPassengerId(passengerId, pageable);
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        return allByPassengerId
                .map(bookingMapper::toBookingDto);
    }

    @Transactional
    public void deleteById(long id) {
        BookingEntity bookingById = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking with specified id not found"));

        FlightEntity flight = bookingById.getFlight();
        flight.getFlightDetail().increaseAvailableSeats();
        flightRepository.save(flight);

        bookingById.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(bookingById);
    }

    @Transactional
    public BookingDto create(CreateBookingRequest request) {
        FlightEntity flightEntity = flightRepository.findById(request.flightId())
                .orElseThrow(() -> new NotFoundException("Flight with specified id not found"));
        UserEntity userEntity = userRepository.findById(request.passengerId())
                .orElseThrow(() -> new NotFoundException("User with specified id not found"));

        FlightDetailEntity flightDetails = flightEntity.getFlightDetail();
        if (flightDetails.getAvailableSeats() <= 0) {
            throw new NotEnoughSeatsException("Flight does not have enough seats");
        }

        flightDetails.decreaseAvailableSeats();
        flightRepository.save(flightEntity);

        BookingEntity bookingEntityFromRequest = bookingMapper.toBookingEntity(request, flightEntity, userEntity);
        BookingEntity savedBooking = bookingRepository.save(bookingEntityFromRequest);

        return bookingMapper.toBookingDto(savedBooking);
    }
}
