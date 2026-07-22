package be.ucll.campus.error;

public class ReservationStartMustBeBeforeEndException extends RuntimeException {
    public ReservationStartMustBeBeforeEndException(String message) {
        super(message);
    }
}
