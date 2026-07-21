package be.ucll.campus.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "room_type")
    private String roomType;

    @Column(name = "number_of_seats")
    private int numberOfSeats;

    @Column(name = "floor")
    private int floor;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "campus_name")
    private Campus campus;

    protected Room() {}

    public Room(String name, String roomType, int numberOfSeats, int floor) {
        this.name = name;
        this.roomType = roomType;
        this.numberOfSeats = numberOfSeats;
        this.floor = floor;
    }

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

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    public void updateDetails(String name, String roomType, int numberOfSeats, int floor) {
        this.name = name;
        this.roomType = roomType;
        this.numberOfSeats = numberOfSeats;
        this.floor = floor;
    }
}
