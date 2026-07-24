package be.ucll.campus.api.error;

public class ReservationNeedsAnEndTimeException extends RuntimeException {
    public ReservationNeedsAnEndTimeException(String message) {
        super(message);
    }
}
