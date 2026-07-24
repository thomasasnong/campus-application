package be.ucll.campus.api.repository.jpa;

import be.ucll.campus.api.model.Reservation;
import be.ucll.campus.api.model.Room;
import be.ucll.campus.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationJpaRepository extends JpaRepository<Reservation,Long> {

    List<Reservation> findAllByUser(User user);

    Optional<Reservation> findByIdAndUser(long reservationId, User user);

    List<Reservation> findAllByRoomsContaining(Room room);
}
