package be.ucll.campus.error;

public class RoomNeedsValidNumberOfSeatsException extends RuntimeException {
    public RoomNeedsValidNumberOfSeatsException(String message) {
        super(message);
    }
}
