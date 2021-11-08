package com.app.todo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    /**
     * Get all registered users.
     * @return A list of registered users.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Get a specific user with a given userID.
     * @param userId
     * @return A registered user with a given userID. If it is already existed, throw an UserNotFoundException.
     * @throws UserNotFoundException
     */
    public User getUser(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    /**
     * Get a specific user with a given email.
     * @param email
     * @return A registered user with a given email. If it is already existed, throw an UserNotFoundException.
     * @throws UserNotFoundException
     */
    public User getUser(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    /**
     * Add a new User.
     * @param user
     * @return A newly registered user. If it is already existed, throw an UserNotFoundException.
     * @throws UserAlreadyRegisteredException
     */
    public User addUser(User user) throws UserAlreadyRegisteredException {
        String email = user.getEmail();

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyRegisteredException(email);
        }
        
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(User updatedUser) throws UserNotFoundException {
//        String email = updatedUser.getEmail();
//
//        if (!userRepository.existsByEmail(email)) {
//            throw new UserNotFoundException(email);
//        }

        User user = getUser(updatedUser.getId());

        user.setCovidStatus(updatedUser.getCovidStatus());
        user.setShnStatus(updatedUser.getShnStatus());

        return userRepository.save(user);
    }

    // TODO: Implement other user services
}