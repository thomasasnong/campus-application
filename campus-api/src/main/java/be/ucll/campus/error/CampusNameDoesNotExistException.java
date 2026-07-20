package be.ucll.campus.error;

public class CampusNameDoesNotExistException extends RuntimeException {
    public CampusNameDoesNotExistException(String message) {
        super(message);
    }
}
