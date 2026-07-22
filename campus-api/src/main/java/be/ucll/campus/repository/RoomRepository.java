package be.ucll.campus.repository;

import be.ucll.campus.model.Campus;
import be.ucll.campus.model.Room;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {

    List<Room> getRoomsByCampus(Campus campus);

    Optional<Room> findRoomById(long roomId);

    Optional<Room> findRoomByIdAndCampus(long roomId,  Campus campus);

    Optional<Room> findRoomByNameAndCampus(String roomName, Campus campus);

    Room saveRoom(Room room);

    void deleteRoom(Room room);
}
