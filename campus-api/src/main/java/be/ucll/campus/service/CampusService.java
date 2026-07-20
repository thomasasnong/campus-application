package be.ucll.campus.service;

import be.ucll.campus.model.Campus;

import java.util.List;

public interface CampusService {

    List<Campus> allCampuses();

    Campus findCampusByName(String campusName);

    Campus addCampus(Campus campus);

    Campus updateCampus(String campusName, Campus campus);

    void removeCampus(String campusName);
}
