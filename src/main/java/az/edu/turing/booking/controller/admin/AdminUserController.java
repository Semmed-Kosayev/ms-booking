package az.edu.turing.booking.controller.admin;

import az.edu.turing.booking.exception.UnauthorizedAccessException;
import az.edu.turing.booking.model.dto.request.UpdateUserDto;
import az.edu.turing.booking.model.dto.response.UserDto;
import az.edu.turing.booking.service.admin.AdminUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/admin/users")
public class AdminUserController {

    private final AdminUserService service;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Min(1) @NotNull @PathVariable Long id, HttpServletRequest request) {
        String role = request.getHeader("role");
        if (!role.equalsIgnoreCase("admin")) {
            throw new UnauthorizedAccessException("Unauthorized access");
        }
        service.deleteById(id);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll(HttpServletRequest request) {
        String role = request.getHeader("role");
        if (!role.equalsIgnoreCase("admin")) {
            throw new UnauthorizedAccessException("Unauthorized access");
        }
        List<UserDto> all = service.getAll();
        return ResponseEntity.ok(all);
    }

    @PutMapping("/passenger/{id}")
    public ResponseEntity<UserDto> update(
            @Min(1) @NotNull @PathVariable Long id,
            @Valid @RequestBody UpdateUserDto updatedUserDto,
            HttpServletRequest request
    ) {
        String role = request.getHeader("role");

        if (!role.equalsIgnoreCase("admin")) {
            throw new UnauthorizedAccessException("Unauthorized access");
        }
        UserDto updatedUser = service.update(id, updatedUserDto);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@Min(1) @NotNull @PathVariable Long id, HttpServletRequest request) {
        String role = request.getHeader("role");

        if (!role.equalsIgnoreCase("admin")) {
            throw new UnauthorizedAccessException("Unauthorized access");
        }
        UserDto byId = service.getById(id);
        return ResponseEntity.ok(byId);
    }
}
