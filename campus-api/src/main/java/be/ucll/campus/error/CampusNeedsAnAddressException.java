package be.ucll.campus.error;

public class CampusNeedsAnAddressException extends RuntimeException {
    public CampusNeedsAnAddressException(String message) {
        super(message);
    }
}
