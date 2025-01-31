package az.edu.turing.booking.controller;

import az.edu.turing.booking.model.dto.request.FlightFilter;
import az.edu.turing.booking.model.dto.response.FlightDto;
import az.edu.turing.booking.model.enums.City;
import az.edu.turing.booking.service.FlightService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
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
            @Parameter(hidden = true)
            @PageableDefault(size = 10, sort = "departureTime", direction = Sort.Direction.ASC)
            Pageable pageable,
            @Valid @ParameterObject
            FlightFilter filter
    ) {
        return ResponseEntity.ok(flightService.getAllFlight(pageable, filter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDto> getById(@Min(1) @NotNull @PathVariable Long id) {
        return ResponseEntity.ok(flightService.getById(id));
    }
}
