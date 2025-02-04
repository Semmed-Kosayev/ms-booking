package az.edu.turing.booking.controller.admin;

import az.edu.turing.booking.model.dto.request.CreateTicketRequest;
import az.edu.turing.booking.model.dto.response.TicketDto;
import az.edu.turing.booking.service.admin.AdminTicketService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/admin/tickets")
public class AdminTicketController {

    private final AdminTicketService service;

    @PostMapping
    public ResponseEntity<TicketDto> create(
            @Valid @RequestBody CreateTicketRequest request,
            @Min(1) @NotNull @RequestHeader("Admin-Id") Long adminId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request, adminId));
    }
}
