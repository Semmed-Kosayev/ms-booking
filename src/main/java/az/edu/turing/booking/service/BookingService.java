package az.edu.turing.booking.service;

import az.edu.turing.booking.domain.entity.BookingEntity;
import az.edu.turing.booking.domain.repository.BookingRepository;
import az.edu.turing.booking.exception.NotFoundException;
import az.edu.turing.booking.mapper.BookingMapper;
import az.edu.turing.booking.model.dto.response.ResponseBookingDto;
import az.edu.turing.booking.model.enums.BookingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository repository;
    private final BookingMapper mapper;

    public ResponseBookingDto getById(Long id) {
        BookingEntity bookingById = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking with specified id not found"));

        return mapper.toResponseBookingDto(bookingById);
    }

    @Transactional
    public void deleteById(long id) {
        BookingEntity bookingById = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Booking with specified id not found"));
        bookingById.setStatus(BookingStatus.CANCELLED);
        repository.save(bookingById);
    }
}
