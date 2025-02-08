package az.edu.turing.booking.controller;

import az.edu.turing.booking.exception.NotFoundException;
import az.edu.turing.booking.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static az.edu.turing.booking.common.JsonFiles.BOOKING_RESPONSE;
import static az.edu.turing.booking.common.JsonFiles.CREATE_BOOKING_REQUEST_JSON;
import static az.edu.turing.booking.common.JsonFiles.CREATE_BOOKING_RESPONSE_JSON;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookingController.class)
class BookingControllerTest {

    public static final String BASE_PATH = "/api/v1/bookings";

    @MockBean
    BookingService bookingService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getById_ShouldReturnBookingDto() throws Exception {
        given(bookingService.getById(BOOKING_ID)).willReturn(BOOKING_DTO);

        var response = json(BOOKING_RESPONSE);

        mockMvc.perform(get(BASE_PATH + "/" + BOOKING_ID)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(response));

        then(bookingService).should(times(1)).getById(BOOKING_ID);
    }

    @Test
    void getById_ShouldReturnNotFound() throws Exception {
        String errorMessage = "Booking with specified id not found";
        given(bookingService.getById(BOOKING_ID)).willThrow(new NotFoundException(errorMessage));

        mockMvc.perform(get(BASE_PATH + "/" + BOOKING_ID)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("not_found"))
                .andExpect(jsonPath("$.errorMessage").value(errorMessage))
                .andExpect(jsonPath("$.requestId").exists())
                .andExpect(jsonPath("$.localDateTime").exists());

        then(bookingService).should(times(1)).getById(BOOKING_ID);
    }

    @Test
    void getAllByPassengerId_ShouldReturnBookingsPageable() throws Exception {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("flight.departureTime").ascending());
        Page<BookingDto> bookingDtos = new PageImpl<>(LIST_BOOKING_DTO, pageable, LIST_BOOKING_DTO.size());

        given(bookingService.getAllByPassengerId(PASSENGER_ID, pageable)).willReturn(bookingDtos);

        var response = json(LIST_BOOKING_RESPONSE);

        mockMvc.perform(get(BASE_PATH + "/passenger/" + PASSENGER_ID)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(response));

        then(bookingService).should(times(1)).getAllByPassengerId(PASSENGER_ID, pageable);
    }

    @Test
    void getAllByPassengerId_ShouldReturnEmptyPage_WhenNoBookingsAvailable() throws Exception {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("flight.departureTime").ascending());
        Page<BookingDto> emptyPage = Page.empty();

        given(bookingService.getAllByPassengerId(PASSENGER_ID, pageable)).willReturn(emptyPage);

        mockMvc.perform(get(BASE_PATH + "/passenger/" + PASSENGER_ID)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());

        then(bookingService).should(times(1)).getAllByPassengerId(PASSENGER_ID, pageable);
    }

    @Test
    void delete_ShouldReturnNoContent() throws Exception {
        willDoNothing().given(bookingService).deleteById(BOOKING_ID);

        mockMvc.perform(delete(BASE_PATH + "/" + BOOKING_ID)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());

        then(bookingService).should(times(1)).deleteById(BOOKING_ID);
    }

    @Test
    void create_ShouldReturnCreatedBookingDto() throws Exception {
        given(bookingService.create(CREATE_BOOKING_REQUEST)).willReturn(CREATE_BOOKING_DTO);

        var requestJson = json(CREATE_BOOKING_REQUEST_JSON);
        var responseJson = json(CREATE_BOOKING_RESPONSE_JSON);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(responseJson));

        then(bookingService).should(times(1)).create(CREATE_BOOKING_REQUEST);
    }
}

