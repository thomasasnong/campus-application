package be.ucll.campus.error;

public class ReservationNeedsAStartTimeException extends RuntimeException {
    public ReservationNeedsAStartTimeException(String message) {
        super(message);
    }
}
