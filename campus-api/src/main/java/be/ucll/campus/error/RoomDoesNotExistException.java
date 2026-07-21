package be.ucll.campus.error;

public class RoomDoesNotExistException extends RuntimeException {
    public RoomDoesNotExistException(String message) {
        super(message);
    }
}
