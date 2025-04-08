package conectaTEA.conectaTEA.repositories;

import conectaTEA.conectaTEA.models.Room;
import conectaTEA.conectaTEA.models.RoomInvites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomInvitesRepository extends JpaRepository<RoomInvites, Long> {

}
