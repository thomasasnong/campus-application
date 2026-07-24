package be.ucll.campus.api.repository.jpa;

import be.ucll.campus.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    List<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
}
