package be.ucll.campus.api.error;

public class CampusNeedsAnAddressException extends RuntimeException {
    public CampusNeedsAnAddressException(String message) {
        super(message);
    }
}
