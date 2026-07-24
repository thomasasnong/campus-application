package be.ucll.campus.api.error;

public class ReservationNeedsAStartTimeException extends RuntimeException {
    public ReservationNeedsAStartTimeException(String message) {
        super(message);
    }
}
