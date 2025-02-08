package az.edu.turing.booking.common;

import az.edu.turing.booking.model.dto.request.CreateBookingRequest;
import az.edu.turing.booking.model.dto.response.BookingDto;

import java.util.Collections;
import java.util.List;

public class TestConstantBooking {
    Long BOOKING_ID = 1L;
    Long PASSENGER_ID = 1L;
    Long NOT_EXIST_BOOKING_ID = 99L;

    BookingDto BOOKING_DTO = getBookingDto();

    List<BookingDto> LIST_BOOKING_DTO = Collections.singletonList(BOOKING_DTO);

    CreateBookingRequest CREATE_BOOKING_REQUEST = CreateBookingRequest.builder()
            .passengerId(1L)
            .flightId(1L)
            .seatNumber("12A")
            .build();

    BookingDto CREATE_BOOKING_DTO = BookingDto.builder()
            .id(1L)
            .passengerId(1L)
            .flightId(1L)
            .seatNumber("12A")
            .baggageWeight(20)
            .specialRequest("Vegetarian meal")
            .status("CONFIRMED")
            .build();

    static BookingDto getBookingDto() {
        return BookingDto.builder()
                .id(1L)
                .passengerId(1L)
                .flightId(1L)
                .seatNumber("10B")
                .baggageWeight(25)
                .specialRequest("Window seat")
                .status("CONFIRMED")
                .build();
    }
}
