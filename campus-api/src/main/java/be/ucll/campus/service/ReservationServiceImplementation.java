package be.ucll.campus.service;

import be.ucll.campus.error.*;
import be.ucll.campus.model.Reservation;
import be.ucll.campus.model.Room;
import be.ucll.campus.model.User;
import be.ucll.campus.repository.ReservationRepository;
import be.ucll.campus.repository.RoomRepository;
import be.ucll.campus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationServiceImplementation implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public ReservationServiceImplementation(ReservationRepository reservationRepository,  UserRepository userRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Reservation> getReservationsByUser(long userId) {
        User user = findUserById(userId);

        return reservationRepository.getReservationsByUser(user);
    }

    @Override
    public Reservation findReservationByIdAndUser(long userId, long reservationId) {
        User user = findUserById(userId);

        return reservationRepository.findReservationByIdAndUser(reservationId, user).orElseThrow(
                () -> new ReservationDoesNotExistException("Reservation with id " + reservationId + " does not exist for user with id " + userId)
        );
    }

    @Override
    public Reservation addReservation(long userId, Reservation reservation) {
        User user = findUserById(userId);

        validateReservationTimes(reservation.getStartTime(), reservation.getEndTime());

        user.addReservation(reservation);

        return reservationRepository.saveReservation(reservation);
    }

    @Override
    public Reservation updateReservation(long userId, long reservationId, Reservation reservation) {
        Reservation originalReservation = findReservationByIdAndUser(userId, reservationId);

        validateReservationTimes(reservation.getStartTime(), reservation.getEndTime());

        validateRoomsAvailableForPeriod(originalReservation, reservation.getStartTime(), reservation.getEndTime());

        originalReservation.updateDetails(reservation.getStartTime(), reservation.getEndTime(), reservation.getComment());

        return reservationRepository.saveReservation(originalReservation);
    }

    @Override
    public void removeReservation(long userId, long reservationId) {
        Reservation reservation = findReservationByIdAndUser(userId, reservationId);

        reservationRepository.deleteReservation(reservation);
    }

    @Override
    public Reservation addRoomToReservation(long userId, long reservationId, long roomId) {
        Reservation reservation = findReservationByIdAndUser(userId, reservationId);

        Room room = roomRepository.findRoomById(roomId).orElseThrow(
                () -> new RoomDoesNotExistException("Room with id " + roomId + " does not exist")
        );

        validateRoomNotAlreadyAdded(reservation, room);

        validateRoomAvailable(room, reservation.getId(), reservation.getStartTime(), reservation.getEndTime());

        reservation.addRoom(room);

        return reservationRepository.saveReservation(reservation);
    }

    private User findUserById(long userId) {
        return userRepository.findUserById(userId).orElseThrow(
                () -> new UserDoesNotExistException("User with id " + userId + " does not exist")
        );
    }

    private void validateReservationTimes(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null) {
            throw new ReservationNeedsAStartTimeException("Reservation start time is null");
        }

        if (endTime == null) {
            throw new ReservationNeedsAnEndTimeException("Reservation end time is null");
        }

        if (!startTime.isBefore(endTime)) {
            throw new ReservationStartMustBeBeforeEndException("Reservation start time must be before end time");
        }

        if (endTime.isBefore(LocalDateTime.now())) {
            throw new ReservationCannotBeInPastException("Reservation end time cannot be in the past");
        }
    }

    private void validateRoomNotAlreadyAdded(Reservation reservation, Room room) {
        for (Room reservedRoom : reservation.getRooms()) {
            if (reservedRoom.getId() == room.getId()) {
                throw new RoomAlreadyInReservationException("Room with id " + room.getId() + " is already part of reservation with id " + reservation.getId());
            }
        }
    }

    private boolean periodsOverlap(LocalDateTime newStart, LocalDateTime newEnd, LocalDateTime existingStart, LocalDateTime existingEnd) {
        return newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart);
    }

    private void validateRoomAvailable(Room room, long currentReservationId, LocalDateTime startTime, LocalDateTime endTime) {
        List<Reservation> reservations = reservationRepository.getReservationsByRoom(room);

        for (Reservation existingReservation : reservations) {
            if (existingReservation.getId() == currentReservationId) {
                continue;
            }

            if (periodsOverlap(startTime, endTime, existingReservation.getStartTime(), existingReservation.getEndTime())) {
                throw new RoomNotAvailableException("Room with id " + room.getId() + " is already reserved during this period");
            }
        }
    }

    private void validateRoomsAvailableForPeriod(Reservation reservation, LocalDateTime startTime, LocalDateTime endTime) {
        for (Room room : reservation.getRooms()) {
            validateRoomAvailable(room, reservation.getId(), startTime, endTime);
        }
    }
}
