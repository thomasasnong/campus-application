package be.ucll.campus.api.service;

import be.ucll.campus.api.model.Campus;

import java.util.List;

public interface CampusService {

    List<Campus> allCampuses();

    Campus findCampusByName(String campusName);

    Campus addCampus(Campus campus);

    Campus updateCampus(String campusName, Campus campus);

    void removeCampus(String campusName);
}
