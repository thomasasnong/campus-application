package be.ucll.campus.repository;

import be.ucll.campus.model.User;
import be.ucll.campus.repository.jpa.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryJpaImplementation implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Autowired
    public UserRepositoryJpaImplementation(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public List<User> getUsers() {
        return userJpaRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(long userId) {
        return userJpaRepository.findById(userId);
    }

    @Override
    public List<User> findUserByNameMatches(String nameMatches) {
        return userJpaRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(nameMatches, nameMatches);
    }

    @Override
    public User saveUser(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userJpaRepository.delete(user);
    }
}
