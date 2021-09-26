package com.app.todo.user;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
//    private BCryptPasswordEncoder encoder;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
//        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User: " + username + " is not found!")
        );
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User addUser(User newUser) {
        newUser.setPassword(newUser.getPassword());
        return userRepository.save(newUser);
    }

    public User deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        User deleteUser = userRepository.findById(userId).get();
        userRepository.deleteById(userId);
        return deleteUser;
    }
}
