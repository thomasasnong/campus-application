package be.ucll.campus.error;

public class RoomAlreadyInReservationException extends RuntimeException {
    public RoomAlreadyInReservationException(String message) {
        super(message);
    }
}
