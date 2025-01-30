package az.edu.turing.booking.controller.admin;

import az.edu.turing.booking.exception.UnauthorizedAccessException;
import az.edu.turing.booking.model.dto.BookingDto;
import az.edu.turing.booking.model.dto.request.BookingUpdateRequest;
import az.edu.turing.booking.model.dto.response.ResponseBookingDto;
import az.edu.turing.booking.model.dto.response.UserDto;
import az.edu.turing.booking.service.admin.AdminBookingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/bookings")
public class AdminBookingController {

    private final AdminBookingService service;

    @GetMapping
    public ResponseEntity<List<ResponseBookingDto>> getAll(HttpServletRequest request) {
        String role = request.getHeader("role");

        if (!role.equalsIgnoreCase("admin")) {
            throw new UnauthorizedAccessException("Unauthorized access");
        }

        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBookingDto> update(
            @Min(1) @PathVariable long id,
            HttpServletRequest request,
            @Valid BookingUpdateRequest updateRequest
    ) {
        String role = request.getHeader("role");

        if (!role.equalsIgnoreCase("admin")) {
            throw new UnauthorizedAccessException("Unauthorized access");
        }

        return ResponseEntity.ok(service.update(id, updateRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBookingDto> getById(@Min(1) @PathVariable long id, HttpServletRequest request) {
        String role = request.getHeader("role");

        if (!role.equalsIgnoreCase("admin")) {
            throw new UnauthorizedAccessException("Unauthorized access");
        }

        ResponseBookingDto byId = service.getById(id);
        return ResponseEntity.ok(byId);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@Min(1) @PathVariable long id, HttpServletRequest request) {
        String role = request.getHeader("role");
        if (!role.equalsIgnoreCase("admin")) {
            throw new UnauthorizedAccessException("Unauthorized access");
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
