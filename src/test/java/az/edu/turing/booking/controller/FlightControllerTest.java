package az.edu.turing.booking.controller;

import az.edu.turing.booking.exception.NotFoundException;
import az.edu.turing.booking.model.dto.request.FlightFilter;
import az.edu.turing.booking.model.dto.response.FlightDto;
import az.edu.turing.booking.service.FlightService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;

import static az.edu.turing.booking.common.FileUtil.json;
import static az.edu.turing.booking.common.JsonFiles.FLIGHT_RESPONSE;
import static az.edu.turing.booking.common.JsonFiles.LIST_FLIGHT_RESPONSE;
import static az.edu.turing.booking.common.TestConstant.FLIGHT_DTO;
import static az.edu.turing.booking.common.TestConstant.FLIGHT_ID;
import static az.edu.turing.booking.common.TestConstant.LIST_FLIGHT_DTO;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FlightController.class)
class FlightControllerTest {

    public static final String BASE_PATH = "/api/v1/flights";

    @MockBean
    FlightService flightService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getAll_ShouldReturnFlightsPageable() throws Exception {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("departureTime").ascending());
        FlightFilter filter = new FlightFilter(null, null, null, null, null, null);
        Page<FlightDto> flightDtos = new PageImpl<>(LIST_FLIGHT_DTO, pageable, LIST_FLIGHT_DTO.size());

        given(flightService.getAllFlight(pageable, filter)).willReturn(flightDtos);

        var response = json(LIST_FLIGHT_RESPONSE);

        mockMvc.perform(get(BASE_PATH)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(response));

        then(flightService).should(times(1)).getAllFlight(pageable, filter);
    }

    @Test
    void getAll_ShouldReturnEmptyPage_WhenNoFlightsAvailable() throws Exception {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("departureTime").ascending());
        FlightFilter filter = new FlightFilter(null, null, null, null, null, null);
        Page<FlightDto> emptyPage = Page.empty();

        given(flightService.getAllFlight(pageable, filter)).willReturn(emptyPage);

        mockMvc.perform(get(BASE_PATH)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());

        then(flightService).should(times(1)).getAllFlight(pageable, filter);
    }

    @Test
    void getById_ShouldReturnFlightDto() throws Exception {
        given(flightService.getById(FLIGHT_ID)).willReturn(FLIGHT_DTO);

        var respone = json(FLIGHT_RESPONSE);

        mockMvc.perform(get(BASE_PATH + "/" + FLIGHT_ID)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(respone));

        then(flightService).should(times(1)).getById(FLIGHT_ID);
    }

    @Test
    void getById_ShouldReturnNotFound() throws Exception {
        String errorMessage = "Flight with specified id not found";
        given(flightService.getById(FLIGHT_ID)).willThrow(new NotFoundException(errorMessage));

        mockMvc.perform(get(BASE_PATH + "/" + FLIGHT_ID)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("not_found"))
                .andExpect(jsonPath("$.errorMessage").value(errorMessage))
                .andExpect(jsonPath("$.requestId").exists())
                .andExpect(jsonPath("$.localDateTime").exists());

        then(flightService).should(times(1)).getById(FLIGHT_ID);
    }
}