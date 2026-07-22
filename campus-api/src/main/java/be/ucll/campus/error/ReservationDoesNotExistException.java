package be.ucll.campus.error;

public class ReservationDoesNotExistException extends RuntimeException {
    public ReservationDoesNotExistException(String message) {
        super(message);
    }
}
