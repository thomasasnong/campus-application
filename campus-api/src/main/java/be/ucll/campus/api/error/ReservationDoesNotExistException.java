package be.ucll.campus.api.error;

public class ReservationDoesNotExistException extends RuntimeException {
    public ReservationDoesNotExistException(String message) {
        super(message);
    }
}
