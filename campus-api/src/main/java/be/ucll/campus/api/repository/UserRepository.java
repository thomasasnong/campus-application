package be.ucll.campus.api.repository;

import java.util.List;
import java.util.Optional;

import be.ucll.campus.api.model.User;

public interface UserRepository {

    List<User> getUsers();

    Optional<User> findUserById(long userId);

    List<User> findUserByNameMatches(String nameMatches);

    User saveUser(User user);

    void deleteUser(User user);
}
