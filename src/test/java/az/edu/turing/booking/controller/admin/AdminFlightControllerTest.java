package az.edu.turing.booking.controller.admin;

import az.edu.turing.booking.exception.NotFoundException;
import az.edu.turing.booking.service.admin.AdminFlightService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static az.edu.turing.booking.common.FileUtil.json;
import static az.edu.turing.booking.common.JsonFiles.CREATE_FLIGHT_REQUEST_JSON;
import static az.edu.turing.booking.common.JsonFiles.CREATE_FLIGHT_RESPONSE;
import static az.edu.turing.booking.common.JsonFiles.UPDATE_FLIGHT_REQUEST_JSON;
import static az.edu.turing.booking.common.JsonFiles.UPDATE_FLIGHT_RESPONSE_JSON;
import static az.edu.turing.booking.common.TestConstant.ADMIN_ID;
import static az.edu.turing.booking.common.TestConstant.CREATE_FLIGHT_DTO;
import static az.edu.turing.booking.common.TestConstant.CREATE_FLIGHT_REQUEST;
import static az.edu.turing.booking.common.TestConstant.FLIGHT_ID;
import static az.edu.turing.booking.common.TestConstant.NOT_EXIST_FLIGHT_ID;
import static az.edu.turing.booking.common.TestConstant.UPDATE_FLIGHT_REQUEST;
import static az.edu.turing.booking.common.TestConstant.UPDATE_FLIGHT_RESPONSE;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AdminFlightController.class)
class AdminFlightControllerTest {

    public static final String BASE_PATH = "/api/v1/admin/flights";

    @MockBean
    AdminFlightService service;

    @Autowired
    MockMvc mockMvc;

    @Test
    void update_ShouldReturnStatusOk_WhenFlightExists() throws Exception {

        given(service.updateFlight(FLIGHT_ID, UPDATE_FLIGHT_REQUEST, ADMIN_ID)).willReturn(UPDATE_FLIGHT_RESPONSE);

        var request = json(UPDATE_FLIGHT_REQUEST_JSON);
        var result = json(UPDATE_FLIGHT_RESPONSE_JSON);

        mockMvc.perform(put(BASE_PATH + "/" + FLIGHT_ID)
                        .contentType(APPLICATION_JSON)
                        .content(request)
                        .header("Admin-Id", ADMIN_ID))
                .andExpect(status().isOk())
                .andExpect(content().json(result));

        then(service).should(times(1)).updateFlight(FLIGHT_ID, UPDATE_FLIGHT_REQUEST, ADMIN_ID);
    }

    @Test
    void update_ShouldReturnStatusOk_WhenFlightNotExists() throws Exception {
        String errorMessage = "Flight with specified id not found";
        given(service.updateFlight(NOT_EXIST_FLIGHT_ID, UPDATE_FLIGHT_REQUEST, ADMIN_ID))
                .willThrow(new NotFoundException(errorMessage));

        var request = json(UPDATE_FLIGHT_REQUEST_JSON);

        mockMvc.perform(put(BASE_PATH + "/" + NOT_EXIST_FLIGHT_ID)
                        .contentType(APPLICATION_JSON)
                        .content(request)
                        .header("Admin-Id", ADMIN_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("not_found"))
                .andExpect(jsonPath("$.errorMessage").value(errorMessage))
                .andExpect(jsonPath("$.requestId").exists())
                .andExpect(jsonPath("$.localDateTime").exists());

        then(service).should(times(1)).updateFlight(NOT_EXIST_FLIGHT_ID, UPDATE_FLIGHT_REQUEST, ADMIN_ID);
    }

    @Test
    void create_ShouldReturnStatusCreated() throws Exception {

        given(service.createFlight(CREATE_FLIGHT_REQUEST, ADMIN_ID)).willReturn(CREATE_FLIGHT_DTO);

        var request = json(CREATE_FLIGHT_REQUEST_JSON);
        var result = json(CREATE_FLIGHT_RESPONSE);

        mockMvc.perform(post(BASE_PATH)
                        .contentType(APPLICATION_JSON)
                        .content(request)
                        .header("Admin-Id", ADMIN_ID))
                .andExpect(status().isCreated())
                .andExpect(content().json(result));

        then(service).should(times(1)).createFlight(CREATE_FLIGHT_REQUEST, ADMIN_ID);
    }

    @Test
    void delete_ShouldReturnNoContent_WhenFlightExists() throws Exception {
        willDoNothing().given(service).deleteById(FLIGHT_ID, ADMIN_ID);

        mockMvc.perform(delete(BASE_PATH + "/" + FLIGHT_ID)
                        .contentType(APPLICATION_JSON)
                        .header("Admin-Id", ADMIN_ID))
                .andExpect(status().isNoContent());

        then(service).should(times(1)).deleteById(FLIGHT_ID, ADMIN_ID);
    }

    @Test
    void delete_ShouldReturnNotFound_WhenFlightDoesNotExist() throws Exception {
        String errorMessage = "Flight with specified id not found";
        doThrow(new NotFoundException(errorMessage)).when(service).deleteById(NOT_EXIST_FLIGHT_ID, ADMIN_ID);

        mockMvc.perform(delete(BASE_PATH + "/" + NOT_EXIST_FLIGHT_ID)
                        .header("Admin-Id", ADMIN_ID)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").value("not_found"))
                .andExpect(jsonPath("$.errorMessage").value(errorMessage));

        then(service).should(times(1)).deleteById(NOT_EXIST_FLIGHT_ID, ADMIN_ID);
    }
}