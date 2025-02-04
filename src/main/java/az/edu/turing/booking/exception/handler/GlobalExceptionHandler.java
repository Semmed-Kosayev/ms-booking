package az.edu.turing.booking.exception.handler;

import az.edu.turing.booking.exception.AlreadyExistsException;
import az.edu.turing.booking.exception.NotEnoughSeatsException;
import az.edu.turing.booking.exception.NotFoundException;
import az.edu.turing.booking.exception.UnauthorizedAccessException;
import az.edu.turing.booking.model.constant.ErrorCode;
import az.edu.turing.booking.model.dto.response.GlobalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.UUID;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<GlobalResponse> alreadyExistsExceptionHandler(AlreadyExistsException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.ALREADY_EXISTS)
                .errorMessage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<GlobalResponse> notFoundExceptionHandler(NotFoundException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.NOT_FOUND)
                .errorMessage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<GlobalResponse> unauthorizedAccessExceptionHandler(UnauthorizedAccessException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.UNAUTHORIZED_ACCESS)
                .errorMessage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(NotEnoughSeatsException.class)
    public ResponseEntity<GlobalResponse> notEnoughSeatsExceptionHandler(NotEnoughSeatsException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.CONFLICT)
                .errorMessage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<GlobalResponse> httpRequestMethodNotSupportedExceptionHandler
            (HttpRequestMethodNotSupportedException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.METHOD_NOT_ALLOWED)
                .errorMessage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<GlobalResponse> httpMediaTypeNotSupportedExceptionHandler
            (HttpMediaTypeNotSupportedException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.UNSUPPORTED_MEDIA_TYPE)
                .errorMessage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<GlobalResponse> httpMediaTypeNotAcceptableExceptionHandler
            (HttpMediaTypeNotAcceptableException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.MEDIA_TYPE_NOT_ACCEPTABLE)
                .errorMessage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<GlobalResponse> missingPathVariableExceptionHandler
            (MissingPathVariableException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.MISSING_PATH_VARIABLE)
                .errorMessage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<GlobalResponse> missingServletRequestParameterExceptionHandler
            (MissingServletRequestParameterException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.MISSING_REQUEST_PARAMETER)
                .errorMessage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<GlobalResponse> missingServletRequestPartExceptionHandler
            (MissingServletRequestPartException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.MISSING_REQUEST_PART)
                .errorMessage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<GlobalResponse> servletRequestBindingExceptionHandler
            (ServletRequestBindingException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.REQUEST_BINDING_FAILED)
                .errorMessage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalResponse> methodArgumentNotValidExceptionHandler
            (MethodArgumentNotValidException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.INVALID_METHOD_ARGUMENT)
                .errorMessage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<GlobalResponse> handlerMethodValidationExceptionHandler
            (HandlerMethodValidationException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.METHOD_VALIDATION_FAILED)
                .errorMessage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<GlobalResponse> noHandlerFoundExceptionHandler
            (NoHandlerFoundException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.NO_HANDLER_FOUND)
                .errorMessage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<GlobalResponse> noResourceFoundExceptionHandler
            (NoResourceFoundException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.NOT_RESOURCE_FOUND)
                .errorMessage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(AsyncRequestTimeoutException.class)
    public ResponseEntity<GlobalResponse> asyncRequestTimeoutExceptionHandler
            (AsyncRequestTimeoutException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.SERVICE_UNAVAILABLE)
                .errorMessage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(ErrorResponseException.class)
    public ResponseEntity<GlobalResponse> errorResponseExceptionHandler(ErrorResponseException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.ERROR_RESPONSE)
                .errorMessage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<GlobalResponse> maxUploadSizeExceededExceptionHandler(MaxUploadSizeExceededException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.MAX_UPLOAD_SIZE_EXCEEDED)
                .errorMessage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(ConversionNotSupportedException.class)
    public ResponseEntity<GlobalResponse> conversionNotSupportedExceptionHandler(ConversionNotSupportedException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.CONVERSION_NOT_SUPPORTED)
                .errorMessage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<GlobalResponse> typeMismatchExceptionHandler(TypeMismatchException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.TYPE_MISMATCH)
                .errorMessage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GlobalResponse> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.MESSAGE_NOT_READABLE)
                .errorMessage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ResponseEntity<GlobalResponse> httpMessageNotWritableExceptionHandler(HttpMessageNotWritableException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.MESSAGE_NOT_WRITABLE)
                .errorMessage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(MethodValidationException.class)
    public ResponseEntity<GlobalResponse> methodValidationExceptionHandler(MethodValidationException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.METHOD_VALIDATION_FAILED)
                .errorMessage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<GlobalResponse> bindExceptionHandler(BindException exception) {
        exceptionLog(exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GlobalResponse.builder()
                .requestId(UUID.randomUUID())
                .errorCode(ErrorCode.BIND_EXCEPTION)
                .errorMessage(exception.getMessage())
                .localDateTime(LocalDateTime.now())
                .build());
    }

    public void exceptionLog(Exception exception) {
        log.info("{} happened {}", exception.getClass().getSimpleName(), exception.getMessage());
    }
}
