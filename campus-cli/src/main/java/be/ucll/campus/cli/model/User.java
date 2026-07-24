package be.ucll.campus.cli.model;

import java.time.LocalDate;

public class User {
    private long id;
    private String lastName;
    private String firstName;
    private LocalDate birthDate;
    private String email;

    public User() {}

    public User(String lastName, String firstName, LocalDate birthDate, String email) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
