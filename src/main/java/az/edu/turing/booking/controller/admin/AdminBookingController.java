package az.edu.turing.booking.controller.admin;

import az.edu.turing.booking.exception.UnauthorizedAccessException;
import az.edu.turing.booking.model.dto.response.ResponseBookingDto;
import az.edu.turing.booking.service.admin.AdminBookingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}
