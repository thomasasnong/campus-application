package be.ucll.campus.api.error;

public class ReservationCannotBeInPastException extends RuntimeException {
    public ReservationCannotBeInPastException(String message) {
        super(message);
    }
}
