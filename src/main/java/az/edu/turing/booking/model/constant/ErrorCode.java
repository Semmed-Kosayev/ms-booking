package az.edu.turing.booking.model.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorCode {
    public static final String ALREADY_EXISTS = "already_exists";
    public static final String NOT_FOUND = "not_found";
    public static final String UNAUTHORIZED_ACCESS = "unauthorized_access";
    public static final String CONFLICT = "conflict";
}
