package az.edu.turing.booking.model.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorCode {
    public static final String ALREADY_EXISTS = "already_exists";
    public static final String NOT_FOUND = "not_found";
    public static final String UNAUTHORIZED_ACCESS = "unauthorized_access";
    public static final String CONFLICT = "conflict";
    public static final String METHOD_NOT_ALLOWED = "method_not_allowed";
    public static final String UNSUPPORTED_MEDIA_TYPE = " unsupported_media_type";
    public static final String MEDIA_TYPE_NOT_ACCEPTABLE = "media_type_not_acceptable";
    public static final String MISSING_PATH_VARIABLE = "missing_path_variable";
    public static final String MISSING_REQUEST_PARAMETER = "missing_request_parameter";
    public static final String MISSING_REQUEST_PART = "missing_request_part";
    public static final String REQUEST_BINDING_FAILED = "request_binding_failed";
    public static final String INVALID_METHOD_ARGUMENT = "invalid_method_argument";
    public static final String NO_HANDLER_FOUND = "no_handler_found";
    public static final String SERVICE_UNAVAILABLE = "service_unavailable";
    public static final String MAX_UPLOAD_SIZE_EXCEEDED = "max_upload_size_exceeded";
    public static final String CONVERSION_NOT_SUPPORTED = "conversion_not_supported";
    public static final String TYPE_MISMATCH = "type_mismatch";
    public static final String MESSAGE_NOT_READABLE = "message_not_readable";
    public static final String MESSAGE_NOT_WRITABLE = "message_not_writable";
    public static final String METHOD_VALIDATION_FAILED = "method_validation_failed";
    public static final String BIND_EXCEPTION = "bind_exception";
    public static final String ERROR_RESPONSE ="response_not_found" ;
    public static final String NOT_RESOURCE_FOUND ="no_resource_found" ;
}
