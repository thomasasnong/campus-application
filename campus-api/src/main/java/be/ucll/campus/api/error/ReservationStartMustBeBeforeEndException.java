package be.ucll.campus.api.error;

public class ReservationStartMustBeBeforeEndException extends RuntimeException {
    public ReservationStartMustBeBeforeEndException(String message) {
        super(message);
    }
}
