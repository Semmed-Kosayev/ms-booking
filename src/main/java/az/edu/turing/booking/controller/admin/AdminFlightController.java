package az.edu.turing.booking.controller.admin;

import az.edu.turing.booking.exception.UnauthorizedAccessException;
import az.edu.turing.booking.model.dto.FlightDto;
import az.edu.turing.booking.model.dto.request.UpdateFlightRequest;
import az.edu.turing.booking.model.dto.response.UpdateFlightResponse;
import az.edu.turing.booking.service.FlightService;
import az.edu.turing.booking.service.admin.AdminFlightService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/admin/flights")
public class AdminFlightController {
    private final AdminFlightService service;
    private final FlightService flightService;

    @PutMapping("/{id}")
    public ResponseEntity<UpdateFlightResponse> update(
            @Min(1) @PathVariable long id,
            @Valid @RequestBody UpdateFlightRequest updateFlightRequest,
            HttpServletRequest request
    ) {
        String role = request.getHeader("role");

        if (!role.equalsIgnoreCase("admin")) {
            throw new UnauthorizedAccessException("Unauthorized access");
        }

        UpdateFlightResponse updatedFlight = service.updateFlight(id, updateFlightRequest);
        return ResponseEntity.ok(updatedFlight);
    }

    @GetMapping
    public ResponseEntity<List<FlightDto>> getAll(HttpServletRequest request) {
        String role = request.getHeader("role");
        if (!role.equalsIgnoreCase("ADMIN")) {
            throw new UnauthorizedAccessException("Unauthorized access");
        }
        List<FlightDto> flights = flightService.getAllFlight();
        return ResponseEntity.ok(flights);
    }
}
