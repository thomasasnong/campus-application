package be.ucll.campus.controller;

import be.ucll.campus.error.*;
import be.ucll.campus.model.Room;
import be.ucll.campus.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/campus/{campusName}/rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public List<Room> getAllRooms(
            @PathVariable("campusName") String campusName,
            @RequestParam(value = "availableFrom", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime availableFrom,
            @RequestParam(value = "availableUntil", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime availableUntil,
            @RequestParam(value = "minNumberOfSeats", required = false) Integer minNumberOfSeats)
    {
        return roomService.allRoomsByCampus(campusName, availableFrom, availableUntil, minNumberOfSeats
        );
    }

    @GetMapping("/{roomId}")
    public Room findRoomByIdAndCampus(@PathVariable("campusName") String campusName, @PathVariable("roomId") long roomId) {
        return roomService.findRoomByIdAndCampus(campusName, roomId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Room addRoomToCampus(@PathVariable("campusName" ) String campusName, @RequestBody Room room) {
        return roomService.addRoomToCampus(campusName, room);
    }

    @PutMapping("/{roomId}")
    public Room updateRoom(@PathVariable("campusName") String campusName, @PathVariable("roomId") long roomId, @RequestBody Room room ) {
        return roomService.updateRoom(campusName, roomId, room);
    }

    @DeleteMapping("/{roomId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeRoom(@PathVariable("campusName") String campusName, @PathVariable("roomId") long roomId) {
        roomService.removeRoom(campusName, roomId);
    }

    @ExceptionHandler(CampusNameDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public FieldMessage handleCampusDoesNotExistException(CampusNameDoesNotExistException exception) {
        return new FieldMessage("campusName", exception.getMessage());
    }

    @ExceptionHandler(CampusNeedsANameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldMessage handleCampusNeedsANameException(CampusNeedsANameException exception) {
        return new FieldMessage("campusName", exception.getMessage());
    }

    @ExceptionHandler(RoomDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public FieldMessage handleRoomDoesNotExistException(RoomDoesNotExistException exception) {
        return new FieldMessage("roomId", exception.getMessage());
    }

    @ExceptionHandler(RoomNeedsANameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldMessage handleRoomNeedsANameException(RoomNeedsANameException exception) {
        return new FieldMessage("name", exception.getMessage());
    }

    @ExceptionHandler(RoomNeedsATypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldMessage handleRoomNeedsATypeException(RoomNeedsATypeException exception) {
        return new FieldMessage("roomType", exception.getMessage());
    }

    @ExceptionHandler(RoomNeedsValidNumberOfSeatsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldMessage handleInvalidNumberOfSeatsException(RoomNeedsValidNumberOfSeatsException exception) {
        return new FieldMessage("numberOfSeats", exception.getMessage());
    }

    @ExceptionHandler(RoomNeedsToBeUniqueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldMessage handleRoomNeedsToBeUniqueException(RoomNeedsToBeUniqueException exception) {
        return new FieldMessage("name", exception.getMessage());
    }

    @ExceptionHandler(AvailabilityStartMustBeBeforeEndException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldMessage handleAvailabilityStartMustBeBeforeEndException(AvailabilityStartMustBeBeforeEndException exception) {
        return new FieldMessage("availableFrom", exception.getMessage());
    }
}
