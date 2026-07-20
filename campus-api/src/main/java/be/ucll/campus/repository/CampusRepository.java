package be.ucll.campus.repository;

import be.ucll.campus.model.Campus;

import java.util.List;
import java.util.Optional;

public interface CampusRepository {

    List<Campus> getCampuses();

    Optional<Campus> findCampusByName(String name);

    Campus saveCampus(Campus campus);

    void deleteCampus(Campus campus);

    void removeAllCampuses();
}
