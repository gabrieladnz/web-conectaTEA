package conectaTEA.conectaTEA.repositories;

import conectaTEA.conectaTEA.enumerators.TypeRoomEnum;
import conectaTEA.conectaTEA.models.Room;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
  List<Room> findByRoomType(TypeRoomEnum roomType);
  List<Room> findByUsers_User_Id(Long userId);
}
