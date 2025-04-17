package conectaTEA.conectaTEA.dtos;

import conectaTEA.conectaTEA.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public record UserDTO(
        @NotNull(message = "Favor informar o nome") String name,
        @NotNull(message = "Favor informar username") String username,
        @NotNull(message = "Favor informar email") @Email(message = "Formato de e-mail inválido.") String email,
        @NotNull(message = "Favor informar a password") @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.") String password
){

}
