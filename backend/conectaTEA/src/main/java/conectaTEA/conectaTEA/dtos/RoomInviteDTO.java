package conectaTEA.conectaTEA.dtos;

import lombok.Builder;

@Builder
public record RoomInviteDTO(
        Long senderId,
        Long recipientId,
        Long roomId
) {}