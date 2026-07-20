package be.ucll.campus.repository.jpa;

import be.ucll.campus.model.Campus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampusJpaRepository extends JpaRepository<Campus, String> {
}
