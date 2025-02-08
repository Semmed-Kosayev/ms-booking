package az.edu.turing.booking.controller.admin;

package com.example.project.controller;

import com.example.project.dto.BookingDto;
import com.example.project.request.BookingUpdateRequest;
import com.example.project.service.AdminBookingService;
import com.example.project.util.TestConstant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AdminBookingController.class)
class AdminBookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminBookingService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAll_shouldReturnBookings() throws Exception {
        List<BookingDto> mockBookings = List.of(TestConstant.BOOKING_DTO);
        Mockito.when(service.getAll(TestConstant.ADMIN_ID)).thenReturn(mockBookings);

        mockMvc.perform(get("/api/v1/admin/bookings")
                        .header("Admin-Id", TestConstant.ADMIN_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(mockBookings.size()))
                .andExpect(jsonPath("$[0].id").value(TestConstant.BOOKING_ID));

        Mockito.verify(service).getAll(TestConstant.ADMIN_ID);
    }

    @Test
    void update_shouldReturnUpdatedBooking() throws Exception {
        BookingUpdateRequest updateRequest = TestConstant.UPDATE_REQUEST;
        BookingDto updatedBooking = TestConstant.BOOKING_DTO;

        Mockito.when(service.update(eq(TestConstant.BOOKING_ID), any(BookingUpdateRequest.class), eq(TestConstant.ADMIN_ID)))
                .thenReturn(updatedBooking);

        mockMvc.perform(put("/api/v1/admin/bookings/{id}", TestConstant.BOOKING_ID)
                        .header("Admin-Id", TestConstant.ADMIN_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedBooking.getId()));

        Mockito.verify(service).update(TestConstant.BOOKING_ID, updateRequest, TestConstant.ADMIN_ID);
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/admin/bookings/{id}", TestConstant.BOOKING_ID)
                        .header("Admin-Id", TestConstant.ADMIN_ID))
                .andExpect(status().isNoContent());

        Mockito.verify(service).deleteById(TestConstant.BOOKING_ID, TestConstant.ADMIN_ID);
    }
}

