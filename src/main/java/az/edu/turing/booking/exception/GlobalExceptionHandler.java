package az.edu.turing.booking.exception;

import az.edu.turing.booking.model.constant.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.UUID;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<GlobalResponse> alreadyExistsExceptionHandler(AlreadyExistsException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.ALREADY_EXISTS)
                .errorMEssage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<GlobalResponse> notFoundExceptionHandler(NotFoundException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.NOT_FOUND)
                .errorMEssage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<GlobalResponse> unauthorizedAccessExceptionHandler(UnauthorizedAccessException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.UNAUTHORIZEDACCESS)
                .errorMEssage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    public void exceptionLog(Exception exception) {
        log.info("{} happened {}", exception.getClass().getSimpleName(), exception.getMessage());
    }
}
