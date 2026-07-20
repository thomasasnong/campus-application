package be.ucll.campus.error;

public class CampusNeedsANameException extends RuntimeException {
    public CampusNeedsANameException(String message) {
        super(message);
    }
}
