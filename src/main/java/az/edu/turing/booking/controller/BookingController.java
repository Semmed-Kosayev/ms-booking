package az.edu.turing.booking.controller;

import az.edu.turing.booking.model.dto.request.CreateBookingRequest;
import az.edu.turing.booking.model.dto.response.ResponseBookingDto;
import az.edu.turing.booking.service.BookingService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService service;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBookingDto> getById(@Min(1) @PathVariable long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Min(1) @PathVariable long id) {
        service.deleteById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseBookingDto> save(@RequestBody @Valid CreateBookingRequest request) {
        ResponseBookingDto responseBookingDto = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseBookingDto);
    }
}
