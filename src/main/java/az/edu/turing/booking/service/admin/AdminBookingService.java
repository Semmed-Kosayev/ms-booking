package az.edu.turing.booking.service.admin;

import az.edu.turing.booking.domain.entity.BookingEntity;
import az.edu.turing.booking.domain.entity.FlightEntity;
import az.edu.turing.booking.domain.entity.UserEntity;
import az.edu.turing.booking.domain.repository.BookingRepository;
import az.edu.turing.booking.domain.repository.FlightRepository;
import az.edu.turing.booking.domain.repository.UserRepository;
import az.edu.turing.booking.exception.NotFoundException;
import az.edu.turing.booking.mapper.BookingMapper;
import az.edu.turing.booking.model.dto.BookingDto;
import az.edu.turing.booking.model.dto.request.BookingUpdateRequest;
import az.edu.turing.booking.model.dto.response.ResponseBookingDto;
import az.edu.turing.booking.model.enums.BookingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminBookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    public List<ResponseBookingDto> getAll() {
        return bookingMapper.toResponseBookingDtoList(bookingRepository.findAll());
    }

    @Transactional
    public ResponseBookingDto update(long id, BookingUpdateRequest updateRequest) {
        FlightEntity flightEntity = flightRepository.findById(updateRequest.flightId())
                .orElseThrow(() -> new NotFoundException("Flight with specified id not found"));
        UserEntity userEntity = userRepository.findById(updateRequest.flightId())
                .orElseThrow(() -> new NotFoundException("User with specified id not found"));
        BookingEntity bookingEntity = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with specified id not found"));

        bookingEntity.setSeatNumber(updateRequest.seatNumber());
        bookingEntity.setClassType(updateRequest.classType());
        bookingEntity.setStatus(updateRequest.status());
        bookingEntity.setPassenger(userEntity);
        bookingEntity.setFlight(flightEntity);
        bookingEntity.setFlightDate(flightEntity.getDepartureTime());
        bookingEntity.setUpdatedAt(LocalDateTime.now());
        bookingEntity.setUpdatedBy(updateRequest.adminId());

        BookingEntity save = bookingRepository.save(bookingEntity);

        return bookingMapper.toResponseBookingDto(save);
    }

    public ResponseBookingDto getById(long id) {
        BookingEntity bookingEntity = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking with specified id not found"));
        return bookingMapper.toResponseBookingDto(bookingEntity);
    }

    @Transactional
    public void deleteById(long id) {
        BookingEntity bookingEntity = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking with specified id not found"));
        bookingEntity.setStatus(BookingStatus.CANCELLED);
    }
}
