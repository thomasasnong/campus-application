package be.ucll.campus.error;

public class CampusNeedsToBeUniqueException extends RuntimeException {
    public CampusNeedsToBeUniqueException(String message) {
        super(message);
    }
}
