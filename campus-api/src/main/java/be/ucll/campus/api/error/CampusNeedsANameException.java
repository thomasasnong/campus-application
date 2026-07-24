package be.ucll.campus.api.error;

public class CampusNeedsANameException extends RuntimeException {
    public CampusNeedsANameException(String message) {
        super(message);
    }
}
