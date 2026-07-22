package be.ucll.campus.repository.jpa;

import be.ucll.campus.model.Reservation;
import be.ucll.campus.model.Room;
import be.ucll.campus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationJpaRepository extends JpaRepository<Reservation,Long> {

    List<Reservation> findAllByUser(User user);

    Optional<Reservation> findByIdAndUser(long reservationId, User user);

    List<Reservation> findAllByRoomsContaining(Room room);
}
