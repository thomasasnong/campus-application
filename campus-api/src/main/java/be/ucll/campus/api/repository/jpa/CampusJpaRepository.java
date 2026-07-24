package be.ucll.campus.api.repository.jpa;

import be.ucll.campus.api.model.Campus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampusJpaRepository extends JpaRepository<Campus, String> {
}
