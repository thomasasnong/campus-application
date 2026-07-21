package be.ucll.campus.service;

import be.ucll.campus.model.Campus;
import be.ucll.campus.model.Room;

import java.util.List;

public interface RoomService {

    List<Room> allRoomsByCampus(String campusName);

    Room findRoomByIdAndCampus(String campusName, long roomId);

    Room addRoomToCampus(String campusName, Room room);

    Room updateRoom(String campusName, long roomId, Room room);

    void removeRoom(String campusName, long roomId);
}
