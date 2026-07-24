package be.ucll.campus.api.error;

public class RoomNeedsToBeUniqueException extends RuntimeException {
    public RoomNeedsToBeUniqueException(String message) {
        super(message);
    }
}
