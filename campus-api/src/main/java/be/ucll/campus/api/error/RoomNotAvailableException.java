package be.ucll.campus.api.error;

public class RoomNotAvailableException extends RuntimeException {
    public RoomNotAvailableException(String message) {
        super(message);
    }
}
