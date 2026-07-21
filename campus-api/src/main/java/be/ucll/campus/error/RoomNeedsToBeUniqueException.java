package be.ucll.campus.error;

public class RoomNeedsToBeUniqueException extends RuntimeException {
    public RoomNeedsToBeUniqueException(String message) {
        super(message);
    }
}
