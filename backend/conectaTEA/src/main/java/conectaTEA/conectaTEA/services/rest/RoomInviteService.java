package conectaTEA.conectaTEA.services.rest;

import conectaTEA.conectaTEA.dtos.RoomInviteDTO;
import conectaTEA.conectaTEA.dtos.RoomInviteDTOResponse;

import java.util.List;

public interface RoomInviteService extends BaseService<RoomInviteDTO, RoomInviteDTOResponse> {

    void sendInvite(RoomInviteDTO dto);

    void acceptInvite(Long id);

    void rejectInvite(Long id);

    boolean isDuplicateInvite(Long senderId, Long receiverId, Long roomId);

    List<RoomInviteDTOResponse> getReceivedInvites(Long userId);

}
