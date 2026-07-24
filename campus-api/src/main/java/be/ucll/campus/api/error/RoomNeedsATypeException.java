package be.ucll.campus.api.error;

public class RoomNeedsATypeException extends RuntimeException {
    public RoomNeedsATypeException(String message) {
        super(message);
    }
}
