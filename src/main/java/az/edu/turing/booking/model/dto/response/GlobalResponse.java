package az.edu.turing.booking.model.dto.response;

import az.edu.turing.booking.model.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GlobalResponse {
    private UUID requestId;
    private String errorCode;
    private String errorMessage;
    private LocalDateTime localDateTime;
}
