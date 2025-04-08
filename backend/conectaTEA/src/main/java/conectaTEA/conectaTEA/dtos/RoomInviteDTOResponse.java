package conectaTEA.conectaTEA.dtos;

import conectaTEA.conectaTEA.enumerators.StatusInviteEnum;
import conectaTEA.conectaTEA.models.RoomInvite;
import lombok.Builder;



@Builder
public record RoomInviteDTOResponse(
        Long id,
        Long senderId,
        Long recipientId,
        StatusInviteEnum status
) {
    public static RoomInviteDTOResponse fromEntity(RoomInvite invite) {
        return new RoomInviteDTOResponse(
                invite.getId(),
                invite.getSender().getId(),
                invite.getRecipient().getId(),
                invite.getStatus()
        );
    }
}
