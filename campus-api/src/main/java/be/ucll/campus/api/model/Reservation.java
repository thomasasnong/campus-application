package be.ucll.campus.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "comment")
    private String comment;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "reservation_room", joinColumns = @JoinColumn(name = "reservation_id"), inverseJoinColumns = @JoinColumn(name = "room_id"))
    private List<Room> rooms = new ArrayList<>();

    protected Reservation() {}

    public Reservation(LocalDateTime startTime, LocalDateTime endTime, String comment) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.comment = comment;
    }

    public long getId() {
        return id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getComment() {
        return comment;
    }

    public User getUser() {
        return user;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public int getCapacity() {
        return rooms.stream()
                .mapToInt(Room::getNumberOfSeats)
                .sum();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void updateDetails(LocalDateTime startTime, LocalDateTime endTime, String comment) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.comment = comment;
    }


}
