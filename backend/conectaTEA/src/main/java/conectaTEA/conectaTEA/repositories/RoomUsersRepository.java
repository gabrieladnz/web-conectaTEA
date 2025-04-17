package conectaTEA.conectaTEA.repositories;

import conectaTEA.conectaTEA.models.Room;
import conectaTEA.conectaTEA.models.RoomUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomUsersRepository extends JpaRepository<RoomUsers, Long> {

}
