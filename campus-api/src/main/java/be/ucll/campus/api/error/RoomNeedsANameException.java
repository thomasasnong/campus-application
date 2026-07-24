package be.ucll.campus.api.error;

public class RoomNeedsANameException extends RuntimeException {
    public RoomNeedsANameException(String message) {
        super(message);
    }
}
