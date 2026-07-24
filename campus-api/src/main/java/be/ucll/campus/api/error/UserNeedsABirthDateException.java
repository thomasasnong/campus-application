package be.ucll.campus.api.error;

public class UserNeedsABirthDateException extends RuntimeException {
    public UserNeedsABirthDateException(String message) {
        super(message);
    }
}
