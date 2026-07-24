package be.ucll.campus.api.error;

public class RoomAlreadyInReservationException extends RuntimeException {
    public RoomAlreadyInReservationException(String message) {
        super(message);
    }
}
