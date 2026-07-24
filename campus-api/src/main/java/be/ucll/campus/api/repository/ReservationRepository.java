package be.ucll.campus.api.repository;

import be.ucll.campus.api.model.Reservation;
import be.ucll.campus.api.model.Room;
import be.ucll.campus.api.model.User;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    List<Reservation> getReservationsByUser(User user);

    Optional<Reservation> findReservationByIdAndUser(long reservationId, User user);

    List<Reservation> getReservationsByRoom(Room room);

    Reservation saveReservation(Reservation reservation);

    void deleteReservation(Reservation reservation);
}
