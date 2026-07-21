package be.ucll.campus.error;

public class UserNeedsAFirstNameException extends RuntimeException {
    public UserNeedsAFirstNameException(String message) {
        super(message);
    }
}
