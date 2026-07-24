package be.ucll.campus.api.service;

import be.ucll.campus.api.model.Reservation;

import java.util.List;

public interface ReservationService {

    List<Reservation> getReservationsByUser(long userId);

    Reservation findReservationByIdAndUser(long userId, long reservationId);

    Reservation addReservation(long userId, Reservation reservation);

    Reservation updateReservation(long userId, long reservationId, Reservation reservation);

    void removeReservation(long userId, long reservationId);

    Reservation addRoomToReservation(long userId, long reservationId, long roomId);
}
