package be.ucll.campus.error;

public class UserNeedsAnEmailException extends RuntimeException {
    public UserNeedsAnEmailException(String message) {
        super(message);
    }
}
