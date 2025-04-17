package conectaTEA.conectaTEA.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public record UpdateRequestDTO(Long id, String name, String username, String email) {
}
