package az.edu.turing.booking.controller;

import az.edu.turing.booking.model.dto.FlightDto;
import az.edu.turing.booking.model.enums.City;
import az.edu.turing.booking.service.FlightService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("api/v1/flights")
@RequiredArgsConstructor
@Validated
public class FlightController {
    private final FlightService flightService;

    @GetMapping
    public ResponseEntity<Page<FlightDto>> getAll(
            @PageableDefault(size = 10, sort = "departureTime", direction = Sort.Direction.ASC)
            Pageable pageable,
            @RequestParam(name = "destination", required = false) City destination,
            @RequestParam(name = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(name = "ticketCount", required = false)
            int ticketCount) {
        return ResponseEntity.ok(flightService.getAllFlight(pageable, destination, date, ticketCount));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDto> getById(@Min(1) @PathVariable Long id) {
        return ResponseEntity.ok(flightService.getById(id));
    }
}
