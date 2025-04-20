package conectaTEA.conectaTEA.dtos;

import lombok.Builder;

import java.time.Instant;
import java.time.LocalDateTime;

@Builder
public record TokenDTO(
        String token,
        Instant expiration,
        String name,
        String username,
        Long userId
) {
}
