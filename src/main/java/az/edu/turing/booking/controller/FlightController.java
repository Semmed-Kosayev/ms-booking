package az.edu.turing.booking.controller;

import az.edu.turing.booking.model.dto.FlightDto;
import az.edu.turing.booking.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/f1/flights")
@RequiredArgsConstructor
@Validated
public class FlightController {
    private final FlightService flightService;

    @GetMapping
    public ResponseEntity<Page<FlightDto>> getAllFlights(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "departureTime,desc") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.by(sort)));
        return ResponseEntity.ok(flightService.getAllFlight(pageable));
    }
}
