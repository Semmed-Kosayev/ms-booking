//package az.edu.turing.booking.service;
//
//import az.edu.turing.booking.domain.entity.FlightEntity;
//import az.edu.turing.booking.domain.repository.FlightRepository;
//import az.edu.turing.booking.exception.NotFoundException;
//import az.edu.turing.booking.mapper.FlightMapper;
//import az.edu.turing.booking.model.dto.request.FlightFilter;
//import az.edu.turing.booking.model.dto.response.FlightDto;
//import az.edu.turing.booking.model.enums.City;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.domain.Specification;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.eq;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.verifyNoInteractions;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class FlightServiceTest {
//
//    @Mock
//    private FlightRepository flightRepository;
//
//    @Mock
//    private FlightMapper flightMapper;
//
//    @InjectMocks
//    private FlightService flightService;
//
//    private FlightEntity flightEntity;
//    private FlightDto flightDto;
//
//    @BeforeEach
//    void setUp() {
//        flightEntity = new FlightEntity();
//        flightEntity.setId(1L);
//        flightEntity.setAirlineName("Emirates");
//        flightEntity.setDepartureTime(LocalDateTime.of(2025, 7, 20, 8, 15));
//        flightEntity.setArrivalTime(LocalDateTime.of(2025, 7, 20, 12, 45));
//        flightEntity.setDepartureAirport("DXB");
//        flightEntity.setArrivalAirport("LAX");
//        flightEntity.setDepartureCity(City.LONDON);
//        flightEntity.setArrivalCity(City.DELHI);
//
//        flightDto = new FlightDto(
//                1L,
//                "Turkish Airlines",
//                LocalDateTime.of(2025, 5, 10, 14, 30),
//                LocalDateTime.of(2025, 5, 10, 18, 45),
//                "IST",
//                "JFK",
//                "Istanbul",
//                "New York",
//                "Boeing 777",
//                "Terminal 1",
//                "Terminal 4",
//                12,
//                30,
//                true,
//                150,
//                180,
//                "On Time"
//        );
//    }
//
//    @Test
//    void getAllFlight_ShouldReturnPagedFlights() {
//        FlightFilter filter = new FlightFilter(null, null, null, null, null, null);
//
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending());
//        Page<FlightEntity> flightPage = new PageImpl<>(List.of(flightEntity), pageable, 1);
//
//        when(flightRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(flightPage);
//        when(flightMapper.toFlightDto(any())).thenReturn(flightDto);
//
//        Page<FlightDto> result = flightService.getAllFlight(pageable, filter);
//
//        assertNotNull(result);
//        assertEquals(1, result.getTotalElements());
//        assertEquals(flightDto.getId(), result.getContent().get(0).getId());
//
//        verify(flightRepository, times(1)).findAll(any(Specification.class), eq(pageable));
//        verify(flightMapper, times(1)).toFlightDto(any());
//    }
//
//    @Test
//    void getById_ShouldReturnFlight_WhenExists() {
//        when(flightRepository.findById(1L)).thenReturn(Optional.of(flightEntity));
//        when(flightMapper.toFlightDto(flightEntity)).thenReturn(flightDto);
//
//        FlightDto result = flightService.getById(1L);
//
//        assertNotNull(result);
//        assertEquals(flightDto.getId(), result.getId());
//
//        verify(flightRepository, times(1)).findById(1L);
//        verify(flightMapper, times(1)).toFlightDto(flightEntity);
//    }
//
//    @Test
//    void getById_ShouldThrowNotFoundException_WhenFlightNotExists() {
//        when(flightRepository.findById(2L)).thenReturn(Optional.empty());
//
//        NotFoundException exception = assertThrows(NotFoundException.class, () -> flightService.getById(2L));
//        assertEquals("Flight with specified id not found", exception.getMessage());
//
//        verify(flightRepository, times(1)).findById(2L);
//        verifyNoInteractions(flightMapper);
//    }
//}
