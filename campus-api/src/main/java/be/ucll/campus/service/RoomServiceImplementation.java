package be.ucll.campus.service;

import be.ucll.campus.error.*;
import be.ucll.campus.model.Campus;
import be.ucll.campus.model.Room;
import be.ucll.campus.repository.CampusRepository;
import be.ucll.campus.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImplementation implements RoomService {

    private final RoomRepository roomRepository;
    private final CampusRepository campusRepository;

    @Autowired
    public RoomServiceImplementation(RoomRepository roomRepository, CampusRepository campusRepository) {
        this.roomRepository = roomRepository;
        this.campusRepository = campusRepository;
    }

    @Override
    public List<Room> allRoomsByCampus(String campusName) {
        Campus campus = findCampusByName(campusName);
        return roomRepository.getRoomsByCampus(campus);
    }

    @Override
    public Room findRoomByIdAndCampus(String campusName, long roomId) {
        Campus campus = findCampusByName(campusName);
        return findRoomByIdAndCampus(roomId, campus);
    }

    @Override
    public Room addRoomToCampus(String campusName, Room room) {
        Campus campus = findCampusByName(campusName);

        validateRoom(room);

        roomRepository.findRoomByNameAndCampus(room.getName(), campus)
                .ifPresent(existingRoom -> {
                    throw new RoomNeedsToBeUniqueException("Room with name " + room.getName() + " already exists on campus " + campusName);
        });

        room.setCampus(campus);

        return roomRepository.saveRoom(room);
    }

    @Override
    public Room updateRoom(String campusName, long roomId, Room room) {
        Campus campus = findCampusByName(campusName);

        Room originalRoom = findRoomByIdAndCampus(roomId, campus);

        validateRoom(room);

        roomRepository.findRoomByNameAndCampus(room.getName(), campus)
                .filter(roomWithSameName ->
                    roomWithSameName.getId() != roomId)
                .ifPresent(existingRoom -> {
                    throw new RoomNeedsToBeUniqueException("Room with name " + room.getName() + " already exists on campus " + campusName);
                });

        originalRoom.updateDetails(room.getName(), room.getRoomType(), room.getNumberOfSeats(), room.getFloor());

        return roomRepository.saveRoom(originalRoom);
    }

    @Override
    public void removeRoom(String campusName, long roomId) {
        Campus campus = findCampusByName(campusName);

        Room room = findRoomByIdAndCampus(roomId, campus);

        roomRepository.deleteRoom(room);
    }

    private Campus findCampusByName(String campusName) {
        if (campusName == null || campusName.isBlank()) {
            throw new CampusNeedsANameException("Campus name is null or blank");
        }

        return campusRepository.findCampusByName(campusName).orElseThrow(
                () -> new CampusNameDoesNotExistException("Campus with name " + campusName + " does not exist")
        );
    }

    private Room findRoomByIdAndCampus(long roomId, Campus campus) {
        return roomRepository.findRoomByIdAndCampus(roomId, campus).orElseThrow(
                () -> new RoomDoesNotExistException("Room with id " + roomId + " does not exist on campus " + campus.getName())
        );
    }

    private void validateRoom(Room room) {
        if (room == null || room.getName() == null || room.getName().isBlank()) {
            throw new RoomNeedsANameException("Room name is null or blank");
        }

        if (room.getRoomType() == null || room.getRoomType().isBlank()) {
            throw new RoomNeedsATypeException("Room type is null or blank");
        }

        if(room.getNumberOfSeats() < 0) {
            throw new RoomNeedsValidNumberOfSeatsException("Room number of seats is negative");
        }
    }
}
