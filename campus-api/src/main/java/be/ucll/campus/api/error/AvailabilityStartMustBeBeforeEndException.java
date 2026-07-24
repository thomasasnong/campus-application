package be.ucll.campus.api.error;

public class AvailabilityStartMustBeBeforeEndException extends RuntimeException {
    public AvailabilityStartMustBeBeforeEndException(String message) {
        super(message);
    }
}
