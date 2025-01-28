package az.edu.turing.booking.service.admin;

import az.edu.turing.booking.domain.repository.BookingRepository;
import az.edu.turing.booking.mapper.BookingMapper;
import az.edu.turing.booking.model.dto.response.ResponseBookingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminBookingService {

    private final BookingRepository repository;
    private final BookingMapper mapper;

    public List<ResponseBookingDto> getAll() {
        return mapper.toResponseBookingDtoList(repository.findAll());
    }
}
