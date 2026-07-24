package be.ucll.campus.api.error;

public class CampusNeedsValidNumberOfParkingSpacesException extends RuntimeException {
    public CampusNeedsValidNumberOfParkingSpacesException(String message) {
        super(message);
    }
}
