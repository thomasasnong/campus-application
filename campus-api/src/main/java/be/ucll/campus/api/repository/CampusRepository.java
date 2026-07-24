package be.ucll.campus.api.repository;

import be.ucll.campus.api.model.Campus;

import java.util.List;
import java.util.Optional;

public interface CampusRepository {

    List<Campus> getCampuses();

    Optional<Campus> findCampusByName(String campusName);

    Campus saveCampus(Campus campus);

    void deleteCampus(Campus campus);

    void removeAllCampuses();
}
