package be.ucll.campus.api.error;

public class UserNeedsAFirstNameException extends RuntimeException {
    public UserNeedsAFirstNameException(String message) {
        super(message);
    }
}
