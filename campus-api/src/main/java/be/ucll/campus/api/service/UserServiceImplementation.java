package be.ucll.campus.api.service;

import be.ucll.campus.api.error.*;
import be.ucll.campus.api.model.User;
import be.ucll.campus.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> allUsers() {
        return userRepository.getUsers();
    }

    @Override
    public List<User> findUsersByNameMatches(String nameMatches) {
        if (nameMatches == null || nameMatches.isBlank()) {
            return userRepository.getUsers();
        }

        return userRepository.findUserByNameMatches(nameMatches);
    }

    @Override
    public User findUserById(long userId) {
        return userRepository.findUserById(userId).orElseThrow(
                () -> new UserDoesNotExistException("User with id " + userId + " does not exist")
        );
    }

    @Override
    public User addUser(User user) {
        validateUser(user);

        return userRepository.saveUser(user);
    }

    @Override
    public User updateUser(long userId, User user) {

        User originalUser = findUserById(userId);

        validateUser(user);

        originalUser.updateDetails(user.getLastName(), user.getFirstName(), user.getBirthDate(), user.getEmail());

        return userRepository.saveUser(originalUser);
    }

    @Override
    public void removeUser(long userId) {
        User user = findUserById(userId);

        userRepository.deleteUser(user);
    }

    private void validateUser(User user) {
        if (user.getFirstName() == null || user.getFirstName().isBlank()) {
            throw new UserNeedsAFirstNameException("User first name is null or blank");
        }

        if (user.getLastName() == null || user.getLastName().isBlank()) {
            throw new UserNeedsALastNameException("User last name is null or blank");
        }

        if (user.getBirthDate() == null) {
            throw new UserNeedsABirthDateException("User birth date is null");
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new UserNeedsAnEmailException("User email is null or blank");
        }
    }
}
