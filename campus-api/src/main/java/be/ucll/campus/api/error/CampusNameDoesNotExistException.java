package be.ucll.campus.api.error;

public class CampusNameDoesNotExistException extends RuntimeException {
    public CampusNameDoesNotExistException(String message) {
        super(message);
    }
}
