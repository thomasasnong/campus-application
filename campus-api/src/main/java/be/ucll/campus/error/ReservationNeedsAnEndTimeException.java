package be.ucll.campus.error;

public class ReservationNeedsAnEndTimeException extends RuntimeException {
    public ReservationNeedsAnEndTimeException(String message) {
        super(message);
    }
}
