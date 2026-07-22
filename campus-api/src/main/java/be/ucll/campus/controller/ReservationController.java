package be.ucll.campus.controller;

import be.ucll.campus.error.*;
import be.ucll.campus.model.Reservation;
import be.ucll.campus.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/{userId}/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> getReservationsByUser(@PathVariable("userId") long userId) {
        return reservationService.getReservationsByUser(userId);
    }

    @GetMapping(name = "/{reservationId}")
    public Reservation findReservationByIdAndUser(@PathVariable("userId") long userId, @PathVariable("reservationId") long reservationId) {
        return reservationService.findReservationByIdAndUser(userId, reservationId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation addReservation(@PathVariable("userId") long userId, @RequestBody Reservation reservation) {
        return reservationService.addReservation(userId, reservation);
    }

    @PutMapping("/{reservationId}")
    public Reservation updateReservation(@PathVariable("userId") long userId, @PathVariable("reservationId") long reservationId, @RequestBody Reservation reservation) {
        return reservationService.updateReservation(userId, reservationId, reservation);
    }

    @PutMapping("/{reservationId}/rooms/{roomId}")
    public Reservation addRoomToReservation(@PathVariable("userId") long userId, @PathVariable("reservationId") long reservationId, @PathVariable("roomId") long roomId) {
        return reservationService.addRoomToReservation(userId, reservationId, roomId);
    }

    @DeleteMapping("/{reservationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeReservation(@PathVariable("userId") long userId, @PathVariable("reservationId") long reservationId) {
        reservationService.removeReservation(userId, reservationId);
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public FieldMessage handleUserDoesNotExistException(UserDoesNotExistException exception) {
        return new FieldMessage("userId", exception.getMessage());
    }

    @ExceptionHandler(ReservationDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public FieldMessage handleReservationDoesNotExistException(ReservationDoesNotExistException exception) {
        return new FieldMessage("reservationId", exception.getMessage());
    }

    @ExceptionHandler(RoomDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public FieldMessage handleRoomDoesNotExistException(RoomDoesNotExistException exception) {
        return new FieldMessage("roomId", exception.getMessage());
    }

    @ExceptionHandler(ReservationNeedsAStartTimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldMessage handleReservationNeedsAStartTimeException(ReservationNeedsAStartTimeException exception) {
        return new FieldMessage("startTime", exception.getMessage());
    }

    @ExceptionHandler(ReservationNeedsAnEndTimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldMessage handleReservationNeedsAnEndTimeException(ReservationNeedsAnEndTimeException exception) {
        return new FieldMessage("endTime", exception.getMessage());
    }

    @ExceptionHandler(ReservationStartMustBeBeforeEndException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldMessage handleReservationStartMustBeBeforeEndException(ReservationStartMustBeBeforeEndException exception) {
        return new FieldMessage("startTime", exception.getMessage());
    }

    @ExceptionHandler(ReservationCannotBeInPastException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldMessage handleReservationCannotBeInPastException(ReservationCannotBeInPastException exception) {
        return new FieldMessage("endTime", exception.getMessage());
    }

    @ExceptionHandler(RoomAlreadyInReservationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldMessage handleRoomAlreadyInReservationException(RoomAlreadyInReservationException exception) {
        return new FieldMessage("roomId", exception.getMessage());
    }

    @ExceptionHandler(RoomNotAvailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldMessage handleRoomNotAvailableException(RoomNotAvailableException exception) {
        return new FieldMessage("roomId", exception.getMessage());
    }
}
