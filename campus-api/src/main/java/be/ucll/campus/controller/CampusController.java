package be.ucll.campus.controller;

import be.ucll.campus.error.*;
import be.ucll.campus.model.Campus;
import be.ucll.campus.service.CampusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/campus")
public class CampusController {

    private final CampusService campusService;

    @Autowired
    public CampusController(CampusService campusService) {
        this.campusService = campusService;
    }

    @GetMapping
    public List<Campus> allCampuses() {
        return campusService.allCampuses();
    }

    @GetMapping("/{campusName}")
    public Campus findCampusByName(@PathVariable String campusName) {
        return campusService.findCampusByName(campusName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Campus addCampus(@RequestBody Campus campus) {
        return campusService.addCampus(campus);
    }

    @PutMapping("/{campusName}")
    public Campus updateCampus(@PathVariable String campusName, @RequestBody Campus campus) {
        return campusService.updateCampus(campusName, campus);
    }

    @DeleteMapping("/{campusName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCampus(@PathVariable String campusName) {
        campusService.removeCampus(campusName);
    }

    @ExceptionHandler(CampusNameDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public FieldMessage handleCampusNotFoundException(CampusNameDoesNotExistException exception) {
        return new FieldMessage("name", exception.getMessage());
    }

    @ExceptionHandler(CampusNeedsANameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldMessage handleCampusNeedsANameException(CampusNeedsANameException exception) {
        return new FieldMessage("name", exception.getMessage());
    }

    @ExceptionHandler(CampusNeedsAnAddressException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldMessage handleCampusNeedsAnAddressException(CampusNeedsAnAddressException exception) {
        return new FieldMessage("address", exception.getMessage());
    }

    @ExceptionHandler(CampusNeedsValidNumberOfParkingSpacesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldMessage handleInvalidNumberOfParkingSpacesException(CampusNeedsValidNumberOfParkingSpacesException exception) {
        return new FieldMessage("numberOfParkingSpaces", exception.getMessage());
    }

    @ExceptionHandler(CampusNeedsToBeUniqueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldMessage handleCampusNeedsToBeUniqueException(CampusNeedsToBeUniqueException exception) {
        return new FieldMessage("name", exception.getMessage());
    }
}
