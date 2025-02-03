package az.edu.turing.booking.controller;

import az.edu.turing.booking.model.dto.request.CreateBookingRequest;
import az.edu.turing.booking.model.dto.response.BookingDto;
import az.edu.turing.booking.service.BookingService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService service;

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> getById(@Min(1) @NotNull @PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<Page<BookingDto>> getAllByPassengerId(
            @Min(1) @NotNull @PathVariable
            Long passengerId,
            @Parameter(hidden = true)
            @PageableDefault(size = 5, sort = "flight.departureTime", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        return ResponseEntity.ok(service.getAllByPassengerId(passengerId, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Min(1) @NotNull @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<BookingDto> create(@RequestBody @Valid CreateBookingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }
}
