package be.ucll.campus.error;

public class ReservationCannotBeInPastException extends RuntimeException {
    public ReservationCannotBeInPastException(String message) {
        super(message);
    }
}
