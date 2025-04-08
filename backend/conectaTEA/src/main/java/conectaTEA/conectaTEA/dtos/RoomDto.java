package conectaTEA.conectaTEA.dtos;

import conectaTEA.conectaTEA.enumerators.TypeRoomEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RoomDto(
        String name,
        String description,
       @NotNull(message = "Provide roomType") TypeRoomEnum roomType,
        List<@Min(value = 1, message = "Please provide usernames") String> usernames
) {


}
