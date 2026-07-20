package be.ucll.campus.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

    protected Campus() {}

    public Campus(String name, String address, int numberOfParkingSpaces) {
        this.name = name;
        this.address = address;
        this.numberOfParkingSpaces = numberOfParkingSpaces;
    }

    public void updateAddressAndParkingSpaces(String address, int numberOfParkingSpaces) {
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
}
