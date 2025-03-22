package conectaTEA.conectaTEA.dtos;

import lombok.Builder;

@Builder
public record LoginDTO(
        String username,
        String password
) {
}
