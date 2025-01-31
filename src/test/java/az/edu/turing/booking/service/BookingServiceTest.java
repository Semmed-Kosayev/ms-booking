package az.edu.turing.booking.service;

import az.edu.turing.booking.domain.entity.BookingEntity;
import az.edu.turing.booking.domain.entity.FlightEntity;
import az.edu.turing.booking.domain.entity.UserEntity;
import az.edu.turing.booking.domain.repository.BookingRepository;
import az.edu.turing.booking.domain.repository.FlightRepository;
import az.edu.turing.booking.domain.repository.UserRepository;
import az.edu.turing.booking.exception.NotFoundException;
import az.edu.turing.booking.mapper.BookingMapper;
import az.edu.turing.booking.model.dto.request.CreateBookingRequest;
import az.edu.turing.booking.model.dto.response.BookingDto;
import az.edu.turing.booking.model.enums.BookingStatus;
import az.edu.turing.booking.model.enums.City;
import az.edu.turing.booking.model.enums.ClassType;
import az.edu.turing.booking.model.enums.Nationality;
import az.edu.turing.booking.model.enums.Role;
import az.edu.turing.booking.model.enums.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookingMapper bookingMapper;

    @InjectMocks
    private BookingService bookingService;

    private BookingEntity bookingEntity;
    private BookingDto bookingDto;
    private CreateBookingRequest createBookingRequest;
    private FlightEntity flightEntity;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        flightEntity = new FlightEntity();
        flightEntity.setId(1L);
        flightEntity.setAirlineName("Turkish Airlines");
        flightEntity.setDepartureTime(LocalDateTime.of(2024, 5, 10, 14, 30));
        flightEntity.setArrivalTime(LocalDateTime.of(2024, 5, 10, 18, 45));
        flightEntity.setDepartureAirport("IST");
        flightEntity.setArrivalAirport("JFK");
        flightEntity.setDepartureCity(City.LONDON);
        flightEntity.setArrivalCity(City.DELHI);

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");
        userEntity.setEmail("john.doe@example.com");
        userEntity.setPhoneNumber("+1234567890");
        userEntity.setDateOfBirth(LocalDate.of(1990, 5, 15));
        userEntity.setNationality(Nationality.USA);
        userEntity.setRole(Role.USER);
        userEntity.setStatus(UserStatus.ACTIVE);

        bookingEntity = new BookingEntity();
        bookingEntity.setId(1L);
        bookingEntity.setFlightDate(LocalDateTime.of(2024, 6, 15, 10, 30));
        bookingEntity.setSeatNumber("12A");
        bookingEntity.setClassType(ClassType.BUSINESS);
        bookingEntity.setStatus(BookingStatus.CONFIRMED);

        bookingDto = new BookingDto(1L, "Emirates", City.LONDON, City.DELHI, "DXB", "LHR",
                LocalDateTime.of(2024, 7, 20, 15, 45),
                LocalDateTime.of(2024, 7, 20, 20, 10),
                "6h 25m", "5B", ClassType.FIRST_CLASS, BookingStatus.CONFIRMED);


        createBookingRequest = new CreateBookingRequest(1L, 1L, ClassType.ECONOMY, "A5");
    }

    @Test
    void getById_ShouldReturnBooking_WhenExists() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(bookingEntity));
        when(bookingMapper.toBookingDto(bookingEntity)).thenReturn(bookingDto);

        BookingDto result = bookingService.getById(1L);

        assertNotNull(result);
        assertEquals(bookingDto.id(), result.id());
        verify(bookingRepository, times(1)).findById(1L);
        verify(bookingMapper, times(1)).toBookingDto(bookingEntity);
    }

    @Test
    void getById_ShouldThrowNotFoundException_WhenBookingNotExists() {
        when(bookingRepository.findById(2L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> bookingService.getById(2L));
        assertEquals("Booking with specified id not found", exception.getMessage());

        verify(bookingRepository, times(1)).findById(2L);
        verifyNoInteractions(bookingMapper);
    }

    @Test
    void getAllByPassengerId_ShouldReturnPagedBookings() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<BookingEntity> bookingPage = new PageImpl<>(List.of(bookingEntity), pageable, 1);

        when(bookingRepository.findAllByPassengerId(1L, pageable)).thenReturn(bookingPage);
        when(bookingMapper.toBookingDto(bookingEntity)).thenReturn(bookingDto);

        Page<BookingDto> result = bookingService.getAllByPassengerId(1L, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(bookingDto.id(), result.getContent().get(0).id());

        verify(bookingRepository, times(1)).findAllByPassengerId(1L, pageable);
        verify(bookingMapper, times(1)).toBookingDto(bookingEntity);
    }

    @Test
    void deleteById_ShouldCancelBooking_WhenExists() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(bookingEntity));

        bookingService.deleteById(1L);

        assertEquals(BookingStatus.CANCELLED, bookingEntity.getStatus());
        verify(bookingRepository, times(1)).save(bookingEntity);
    }

    @Test
    void deleteById_ShouldThrowNotFoundException_WhenBookingNotExists() {
        when(bookingRepository.findById(2L)).thenReturn(Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class, () -> bookingService.deleteById(2L));
        assertEquals("Booking with specified id not found", exception.getMessage());
        verify(bookingRepository, times(1)).findById(2L);
        verify(bookingRepository, never()).save(any());
    }


    @Test
    void create_ShouldCreateBooking_WhenValidRequest() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flightEntity));
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(bookingMapper.toBookingEntity(createBookingRequest, flightEntity, userEntity)).thenReturn(bookingEntity);
        when(bookingRepository.save(bookingEntity)).thenReturn(bookingEntity);
        when(bookingMapper.toBookingDto(bookingEntity)).thenReturn(bookingDto);

        BookingDto result = bookingService.create(createBookingRequest);

        assertNotNull(result);
        assertEquals(bookingDto.id(), result.id());
        verify(flightRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);
        verify(bookingMapper, times(1)).toBookingEntity(createBookingRequest, flightEntity, userEntity);
        verify(bookingRepository, times(1)).save(bookingEntity);
    }

    @Test
    void create_ShouldThrowNotFoundException_WhenFlightNotFound() {
        when(flightRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> bookingService.create(createBookingRequest));
        assertEquals("Flight with specified id not found", exception.getMessage());
        verify(flightRepository, times(1)).findById(1L);
        verifyNoInteractions(userRepository);
        verifyNoInteractions(bookingMapper);
    }

    @Test
    void create_ShouldThrowNotFoundException_WhenUserNotFound() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flightEntity));
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> bookingService.create(createBookingRequest));
        assertEquals("User with specified id not found", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
        verifyNoInteractions(bookingMapper);
    }
}

