package be.ucll.campus.api.error;

public class RoomDoesNotExistException extends RuntimeException {
    public RoomDoesNotExistException(String message) {
        super(message);
    }
}
