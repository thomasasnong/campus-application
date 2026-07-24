package be.ucll.campus.api.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "campus")
public class Campus {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "number_of_parking_spaces")
    private int numberOfParkingSpaces;

    @OneToMany(mappedBy = "campus", cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<>();

    protected Campus() {}

    public Campus(String name, String address, int numberOfParkingSpaces) {
        this.name = name;
        this.address = address;
        this.numberOfParkingSpaces = numberOfParkingSpaces;
    }

    public void updateDetails(String address, int numberOfParkingSpaces) {
        this.address = address;
        this.numberOfParkingSpaces = numberOfParkingSpaces;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getNumberOfParkingSpaces() {
        return numberOfParkingSpaces;
    }

    public int getNumberOfRooms() {
        return rooms.size();
    }
}
