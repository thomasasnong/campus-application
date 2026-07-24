package be.ucll.campus.api.error;

public class UserNeedsAnEmailException extends RuntimeException {
    public UserNeedsAnEmailException(String message) {
        super(message);
    }
}
