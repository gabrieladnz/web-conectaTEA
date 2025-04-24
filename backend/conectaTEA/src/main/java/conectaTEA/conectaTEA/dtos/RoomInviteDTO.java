package conectaTEA.conectaTEA.dtos;

import lombok.Builder;

@Builder
public record RoomInviteDTO(
        Long senderId,
        String recipientUsername,
        Long roomId
) {}