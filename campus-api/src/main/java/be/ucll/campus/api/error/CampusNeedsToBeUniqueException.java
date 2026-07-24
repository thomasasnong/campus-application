package be.ucll.campus.api.error;

public class CampusNeedsToBeUniqueException extends RuntimeException {
    public CampusNeedsToBeUniqueException(String message) {
        super(message);
    }
}
