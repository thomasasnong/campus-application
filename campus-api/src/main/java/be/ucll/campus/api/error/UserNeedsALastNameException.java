package be.ucll.campus.api.error;

public class UserNeedsALastNameException extends RuntimeException {
    public UserNeedsALastNameException(String message) {
        super(message);
    }
}
