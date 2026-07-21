package be.ucll.campus.error;

public class UserNeedsALastNameException extends RuntimeException {
    public UserNeedsALastNameException(String message) {
        super(message);
    }
}
