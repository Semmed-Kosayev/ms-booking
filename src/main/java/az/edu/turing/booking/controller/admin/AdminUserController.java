package az.edu.turing.booking.controller.admin;

import az.edu.turing.booking.exception.UnauthorizedAccessException;
import az.edu.turing.booking.service.admin.AdminUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/admin/users")
public class AdminUserController {

    private final AdminUserService service;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Min(1) @PathVariable long id, HttpServletRequest request) {
        String role = request.getHeader("role");

        if (!role.equalsIgnoreCase("admin")) {
            throw new UnauthorizedAccessException("Unauthorized access");
        }

        service.deleteById(id);
    }
}
