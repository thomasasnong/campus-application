package be.ucll.campus.error;

public class UserNeedsABirthDateException extends RuntimeException {
    public UserNeedsABirthDateException(String message) {
        super(message);
    }
}
