package be.ucll.campus.repository.jpa;

import be.ucll.campus.model.Campus;
import be.ucll.campus.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomJpaRepository extends JpaRepository<Room,Long> {

    List<Room> findAllByCampus(Campus campus);

    Optional<Room> findByIdAndCampus(long roomId, Campus campus);

    Optional<Room> findByNameAndCampus(String roomName, Campus campus);
}
