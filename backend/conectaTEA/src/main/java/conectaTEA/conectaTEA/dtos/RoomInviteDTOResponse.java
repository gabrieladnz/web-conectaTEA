package conectaTEA.conectaTEA.dtos;

import conectaTEA.conectaTEA.enumerators.StatusInviteEnum;
import conectaTEA.conectaTEA.models.RoomInvite;
import lombok.Builder;



@Builder
public record RoomInviteDTOResponse(
        Long id,
        Long senderId,
        String senderName,
        Long recipientId,
        String recipientName,
        String roomName,
        String descriptionRoom,
        String category,
        StatusInviteEnum status
) {
    public static RoomInviteDTOResponse fromEntity(RoomInvite invite) {
        return new RoomInviteDTOResponse(
                invite.getId(),
                invite.getSender().getId(),
                invite.getSender().getName(),
                invite.getRecipient().getId(),
                invite.getRecipient().getName(),
                invite.getRoom().getName(),
                invite.getRoom().getDescription(),
                invite.getRoom().getCategory(),
                invite.getStatus()
        );
    }
}
