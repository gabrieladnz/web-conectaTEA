package conectaTEA.conectaTEA.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record LoginDTO(
        @NotNull(message = "Favor informar username") String username,
        @NotNull(message = "Favor informar a password") @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.") String password
) {
}
