package be.ucll.campus.model;

import java.time.LocalDateTime;

public class Reservation {
    private long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String comment;
    private int capacity;

    public Reservation() {}

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

    public int getCapacity() {
        return capacity;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
