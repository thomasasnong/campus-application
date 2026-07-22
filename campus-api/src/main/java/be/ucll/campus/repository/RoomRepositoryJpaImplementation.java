package be.ucll.campus.repository;

import be.ucll.campus.model.Campus;
import be.ucll.campus.model.Room;
import be.ucll.campus.repository.jpa.RoomJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoomRepositoryJpaImplementation  implements RoomRepository {

    private final RoomJpaRepository roomJpaRepository;

    @Autowired
    public RoomRepositoryJpaImplementation(RoomJpaRepository roomJpaRepository) {
        this.roomJpaRepository = roomJpaRepository;
    }

    @Override
    public List<Room> getRoomsByCampus(Campus campus) {
        return roomJpaRepository.findAllByCampus(campus);
    }

    @Override
    public Optional<Room> findRoomById(long roomId) {
        return roomJpaRepository.findById(roomId);
    }

    @Override
    public Optional<Room> findRoomByIdAndCampus(long roomId, Campus campus) {
        return roomJpaRepository.findByIdAndCampus(roomId, campus);
    }

    @Override
    public Optional<Room> findRoomByNameAndCampus(String roomName, Campus campus) {
        return roomJpaRepository.findByNameAndCampus(roomName, campus);
    }

    @Override
    public Room saveRoom(Room room) {
        return roomJpaRepository.save(room);
    }

    @Override
    public void deleteRoom(Room room) {
        roomJpaRepository.delete(room);
    }
}
