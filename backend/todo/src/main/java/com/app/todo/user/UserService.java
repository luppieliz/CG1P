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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long userId) throws UserNotFoundException {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public User getUser(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    public User addUser(User user) throws UserAlreadyRegisteredException {
        String email = user.getEmail();

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyRegisteredException(email);
        }
        
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // TODO: Implement other user services
}