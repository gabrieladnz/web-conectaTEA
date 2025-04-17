package conectaTEA.conectaTEA.repositories;

import conectaTEA.conectaTEA.enumerators.StatusInviteEnum;
import conectaTEA.conectaTEA.models.RoomInvite;
import conectaTEA.conectaTEA.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomInvitesRepository extends JpaRepository<RoomInvite, Long> {
    Optional<RoomInvite> findBySenderIdAndRecipientIdAndRoomIdAndStatus(
            Long senderId,
            Long recipientId,
            Long roomId,
            StatusInviteEnum status
    );

    List<RoomInvite> findByRecipientAndStatus(User recipient, StatusInviteEnum status);

}
