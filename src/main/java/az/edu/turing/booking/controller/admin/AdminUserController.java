package az.edu.turing.booking.controller.admin;

import az.edu.turing.booking.model.dto.request.UpdateUserDto;
import az.edu.turing.booking.model.dto.response.UserDto;
import az.edu.turing.booking.service.admin.AdminUserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/admin/users")
public class AdminUserController {

    private final AdminUserService service;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Min(1) @NotNull @PathVariable("id")
            Long userId,
            @Min(1) @NotNull @RequestHeader("Admin-Id")
            Long adminId
    ) {
        service.deleteById(userId, adminId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll(@Min(1) @NotNull @RequestHeader("Admin-Id") Long adminId) {
        return ResponseEntity.ok(service.getAll(adminId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(
            @Min(1) @NotNull @PathVariable Long id,
            @Valid @RequestBody UpdateUserDto updatedUserDto
    ) {
        return ResponseEntity.ok(service.update(id, updatedUserDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(
            @Min(1) @NotNull @PathVariable("id") Long userId,
            @Min(1) @NotNull @RequestHeader("Admin-Id") Long adminId
    ) {
        return ResponseEntity.ok(service.getById(userId, adminId));
    }
}
