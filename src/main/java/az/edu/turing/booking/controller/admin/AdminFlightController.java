package az.edu.turing.booking.controller.admin;

import az.edu.turing.booking.model.dto.request.CreateFlightRequest;
import az.edu.turing.booking.model.dto.request.UpdateFlightRequest;
import az.edu.turing.booking.model.dto.response.FlightDto;
import az.edu.turing.booking.model.dto.response.UpdateFlightResponse;
import az.edu.turing.booking.service.admin.AdminFlightService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/admin/flights")
public class AdminFlightController {

    private final AdminFlightService service;

    @PutMapping("/{id}")
    public ResponseEntity<UpdateFlightResponse> update(
            @Min(1) @NotNull @PathVariable("id") Long flightId,
            @Min(1) @NotNull @RequestHeader("Admin-Id") Long adminId,
            @Valid @RequestBody UpdateFlightRequest updateFlightRequest
    ) {
        return ResponseEntity.ok(service.updateFlight(flightId, updateFlightRequest, adminId));
    }

    @PostMapping
    public ResponseEntity<FlightDto> create(
            @Min(1) @NotNull @RequestHeader("Admin-Id") Long adminId,
            @Valid @RequestBody CreateFlightRequest createFlightRequest
    ) {
        return ResponseEntity.ok(service.createFlight(createFlightRequest, adminId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Min(1) @NotNull @PathVariable("id")
            Long flightId,
            @Min(1) @NotNull @RequestHeader("Admin-Id")
            Long adminId
    ) {
        service.deleteById(flightId, adminId);
        return ResponseEntity.noContent().build();
    }
}