package be.ucll.campus.api.service;

import be.ucll.campus.api.error.*;
import be.ucll.campus.api.model.Campus;
import be.ucll.campus.api.model.Reservation;
import be.ucll.campus.api.model.Room;
import be.ucll.campus.api.repository.CampusRepository;
import be.ucll.campus.api.repository.ReservationRepository;
import be.ucll.campus.api.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceImplementation implements RoomService {

    private final RoomRepository roomRepository;
    private final CampusRepository campusRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public RoomServiceImplementation(RoomRepository roomRepository, CampusRepository campusRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.campusRepository = campusRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public List<Room> allRoomsByCampus(String campusName, LocalDateTime availableFrom, LocalDateTime availableUntil, Integer minNumberOfSeats) {
        Campus campus = findCampusByName(campusName);

        if (availableFrom != null && availableUntil != null && !availableFrom.isBefore(availableUntil)) {
            throw new AvailabilityStartMustBeBeforeEndException("Available from must be before available until");
        }

        List<Room> rooms = roomRepository.getRoomsByCampus(campus);

        List<Room> filteredRooms = new ArrayList<>();

        for (Room room : rooms) {
            if (minNumberOfSeats != null && room.getNumberOfSeats() < minNumberOfSeats) {
                continue;
            }

            if (!isRoomAvailable(room, availableFrom, availableUntil)) {
                continue;
            }

            filteredRooms.add(room);
        }

        return filteredRooms;
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
        if (room.getName() == null || room.getName().isBlank()) {
            throw new RoomNeedsANameException("Room name is null or blank");
        }

        if (room.getRoomType() == null || room.getRoomType().isBlank()) {
            throw new RoomNeedsATypeException("Room type is null or blank");
        }

        if(room.getNumberOfSeats() < 0) {
            throw new RoomNeedsValidNumberOfSeatsException("Room number of seats is negative");
        }
    }

    private boolean isRoomAvailable(Room room, LocalDateTime availableFrom, LocalDateTime availableUntil) {
        if (availableFrom == null && availableUntil == null) {
            return true;
        }

        List<Reservation> reservations = reservationRepository.getReservationsByRoom(room);

        for (Reservation reservation : reservations) {
            if (availableFrom != null && availableUntil != null) {
                boolean periodsOverlap = availableFrom.isBefore(reservation.getEndTime()) && availableUntil.isAfter(reservation.getStartTime());

                if (periodsOverlap) {
                    return false;
                }
            } else {
                LocalDateTime moment;

                if (availableFrom != null) {
                    moment = availableFrom;
                } else {
                    moment = availableUntil;
                }

                boolean occupiedAtMoment = !moment.isBefore(reservation.getStartTime()) && moment.isBefore(reservation.getEndTime());

                if (occupiedAtMoment) {
                    return false;
                }
            }
        }

        return true;
    }
}
