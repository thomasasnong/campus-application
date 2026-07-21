package be.ucll.campus.service;

import be.ucll.campus.error.*;
import be.ucll.campus.model.Campus;
import be.ucll.campus.repository.CampusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampusServiceImplementation implements CampusService {

    private final CampusRepository campusRepository;

    @Autowired
    public CampusServiceImplementation(CampusRepository campusRepository) {
        this.campusRepository = campusRepository;
    }

    @Override
    public List<Campus> allCampuses() {
        return campusRepository.getCampuses();
    }

    @Override
    public Campus findCampusByName(String campusName) {
        validateName(campusName);
        return campusRepository.findCampusByName(campusName).orElseThrow(
                () -> new CampusNameDoesNotExistException("Campus with name " + campusName + " does not exist")
        );
    }

    @Override
    public Campus addCampus(Campus campus) {
        validateCampusForCreation(campus);

        campusRepository.findCampusByName(campus.getName()).ifPresent(existingCampus -> {
            throw new CampusNeedsToBeUniqueException("Campus with name " + campus.getName() + " already exists");
        });

        return campusRepository.saveCampus(campus);
    }

    @Override
    public Campus updateCampus(String campusName, Campus campus) {
        Campus originalCampus = findCampusByName(campusName);

        validateCampusDetails(campus);

        originalCampus.updateDetails(campus.getAddress(),campus.getNumberOfParkingSpaces());

        return campusRepository.saveCampus(originalCampus);
    }

    @Override
    public void removeCampus(String campusName) {
        Campus campus = findCampusByName(campusName);

        campusRepository.deleteCampus(campus);
    }

    private void validateName(String campusName) {
        if (campusName == null || campusName.isBlank()) {
            throw new CampusNeedsANameException("Campus name is null or blank");
        }
    }

    private void validateCampusDetails(Campus campus) {
        if (campus == null || campus.getAddress() == null || campus.getAddress().isBlank()) {
            throw new CampusNeedsAnAddressException("Campus address is null or blank");
        }

        if (campus.getNumberOfParkingSpaces() < 0) {
            throw new CampusNeedsValidNumberOfParkingSpacesException("Campus number of parking spaces is negative");
        }
    }

    private void validateCampusForCreation(Campus campus) {
        if (campus == null) {
            throw new CampusNeedsANameException("Campus name is null or blank");
        }

        validateName(campus.getName());
        validateCampusDetails(campus);
    }
}
