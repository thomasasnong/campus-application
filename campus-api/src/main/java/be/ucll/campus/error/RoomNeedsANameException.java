package be.ucll.campus.error;

public class RoomNeedsANameException extends RuntimeException {
    public RoomNeedsANameException(String message) {
        super(message);
    }
}
