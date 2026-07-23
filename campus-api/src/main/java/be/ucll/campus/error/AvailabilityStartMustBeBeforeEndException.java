package be.ucll.campus.error;

public class AvailabilityStartMustBeBeforeEndException extends RuntimeException {
    public AvailabilityStartMustBeBeforeEndException(String message) {
        super(message);
    }
}
