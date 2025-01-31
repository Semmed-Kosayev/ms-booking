package az.edu.turing.booking.controller.admin;

import az.edu.turing.booking.model.dto.request.BookingUpdateRequest;
import az.edu.turing.booking.model.dto.response.BookingDto;
import az.edu.turing.booking.service.admin.AdminBookingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/bookings")
public class AdminBookingController {

    private final AdminBookingService service;

    @GetMapping
    public ResponseEntity<List<BookingDto>> getAll(@Min(1) @NotNull @RequestHeader("Admin-Id") Long adminId) {
        return ResponseEntity.ok(service.getAll(adminId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingDto> update(
            @Min(1) @NotNull @PathVariable Long id,
            @Valid BookingUpdateRequest updateRequest
    ) {
        return ResponseEntity.ok(service.update(id, updateRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getById(
            @Min(1) @NotNull @PathVariable("id") Long bookingId,
            @Min(1) @NotNull @RequestHeader("Admin-Id") Long adminId
    ) {
        BookingDto bookingById = service.getById(bookingId, adminId);
        return ResponseEntity.ok(bookingById);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(
            @Min(1) @NotNull @PathVariable("id") Long bookingId,
            @Min(1) @NotNull @RequestHeader("Admin-Id") Long adminId
    ) {
        service.deleteById(bookingId, adminId);
        return ResponseEntity.noContent().build();
    }
}
