package be.ucll.campus.api.service;

import be.ucll.campus.api.model.User;

import java.util.List;

public interface UserService {

    List<User> allUsers();

    List<User> findUsersByNameMatches(String nameMatches);

    User findUserById(long userId);

    User addUser(User user);

    User updateUser(long userId, User user);

    void removeUser(long userId);
}
