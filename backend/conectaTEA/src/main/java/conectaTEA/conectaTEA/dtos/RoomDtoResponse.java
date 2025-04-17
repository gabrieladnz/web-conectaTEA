package conectaTEA.conectaTEA.dtos;

import conectaTEA.conectaTEA.enumerators.TypeRoomEnum;

import java.util.List;

public record RoomDtoResponse(
        Long id,
        String name,
        String description,
        TypeRoomEnum roomType,
        List<UserDTOResponse> users
) {
}
