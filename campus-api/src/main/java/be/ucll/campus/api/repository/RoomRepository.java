package be.ucll.campus.api.repository;

import be.ucll.campus.api.model.Campus;
import be.ucll.campus.api.model.Room;

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
