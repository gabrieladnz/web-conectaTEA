package conectaTEA.conectaTEA.dtos;

import conectaTEA.conectaTEA.models.User;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public record UserDTO(
        @NotNull String name,
        @NotNull String username,
        @NotNull String email,
        @NotNull String password
){

}
