package conectaTEA.conectaTEA.dtos;

import conectaTEA.conectaTEA.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public record UserDTO(
        @NotNull(message = "Favor informar o nome") String name,
        @NotNull(message = "Favor informar username") String username,
        @NotNull(message = "Favor informar email") @Email(message = "Formato de Email invalido") String email,
        @NotNull(message = "Favor informar a password") @Min(value = 6, message = "no minimo 6 digitos") String password
){

}
