package be.ucll.campus.repository;

import be.ucll.campus.model.Campus;
import be.ucll.campus.model.Reservation;
import be.ucll.campus.model.Room;
import be.ucll.campus.model.User;
import be.ucll.campus.repository.jpa.ReservationJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReservationJpaRepositoryImplementation implements ReservationRepository {

    private final ReservationJpaRepository reservationJpaRepository;

    @Autowired
    public ReservationJpaRepositoryImplementation(ReservationJpaRepository reservationJpaRepository) {
        this.reservationJpaRepository = reservationJpaRepository;
    }

    @Override
    public List<Reservation> getReservationsByUser(User user) {
        return reservationJpaRepository.findAllByUser(user);
    }

    @Override
    public Optional<Reservation> findReservationByIdAndUser(long reservationId, User user) {
        return reservationJpaRepository.findByIdAndUser(reservationId, user);
    }

    @Override
    public List<Reservation> getReservationsByRoom(Room room) {
        return reservationJpaRepository.findAllByRoomsContaining(room);
    }

    @Override
    public Reservation saveReservation(Reservation reservation) {
        return reservationJpaRepository.save(reservation);
    }

    @Override
    public void deleteReservation(Reservation reservation) {
        reservationJpaRepository.delete(reservation);
    }
}
