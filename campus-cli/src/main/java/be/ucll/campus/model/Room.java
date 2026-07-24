package be.ucll.campus.model;

public class Room {
    private long id;
    private String name;
    private String roomType;
    private int numberOfSeats;
    private int floor;
    private String campusName;

    public Room() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getRoomType() {
        return roomType;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public int getFloor() {
        return floor;
    }

    public String getCampusName() {
        return campusName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }
}
