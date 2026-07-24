package be.ucll.campus.api.error;

public class RoomNeedsValidNumberOfSeatsException extends RuntimeException {
    public RoomNeedsValidNumberOfSeatsException(String message) {
        super(message);
    }
}
