package be.ucll.campus.controller;

import be.ucll.campus.error.*;
import be.ucll.campus.model.User;
import be.ucll.campus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> allUsers(@RequestParam(value = "nameMatches", required = false) String nameMatches) {

        if (nameMatches == null || nameMatches.isBlank()) {
            return userService.allUsers();
        }

        return userService.findUsersByNameMatches(nameMatches);
    }

    @GetMapping("/{userId}")
    public User findUserById(@PathVariable("userId") long userId) {
        return userService.findUserById(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable("userId") long userId, @RequestBody User user) {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUser(@PathVariable("userId") long userId) {
        userService.removeUser(userId);
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public FieldMessage handleUserDoesNotExistException(UserDoesNotExistException exception) {
        return new FieldMessage("userId", exception.getMessage());
    }

    @ExceptionHandler(UserNeedsAFirstNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldMessage handleUserNeedsAFirstNameException(UserNeedsAFirstNameException exception) {
        return new FieldMessage("firstName", exception.getMessage());
    }

    @ExceptionHandler(UserNeedsALastNameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldMessage handleUserNeedsALastNameException(UserNeedsALastNameException exception) {
        return new FieldMessage("lastName", exception.getMessage());
    }

    @ExceptionHandler(UserNeedsABirthDateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldMessage handleUserNeedsABirthDateException(UserNeedsABirthDateException exception) {
        return new FieldMessage("birthDate", exception.getMessage());
    }

    @ExceptionHandler(UserNeedsAnEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public FieldMessage handleUserNeedsAnEmailException(UserNeedsAnEmailException exception) {
        return new FieldMessage("email", exception.getMessage());
    }
}
