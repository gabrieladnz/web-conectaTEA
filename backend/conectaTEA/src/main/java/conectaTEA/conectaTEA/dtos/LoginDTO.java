package conectaTEA.conectaTEA.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LoginDTO(
        @NotNull(message = "Favor informar username") String username,
        @NotNull(message = "Favor informar a password") @Min(value = 6, message = "no minimo 6 digitos") String password
) {
}
