package com.app.buddy19.user;

import com.app.buddy19.business.Business;
import com.app.buddy19.business.BusinessNotFoundException;
import com.app.buddy19.business.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;
    private BusinessService businessService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder encoder, BusinessService businessService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.businessService = businessService;
    }

    /**
     * Get all registered users.
     *
     * @return A list of registered users.
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Get all registered users by id.
     *
     * @param userIds
     * @return A list of registered users.
     */
    @Override
    public List<User> getAllUsersById(List<UUID> userIds) {
        return userRepository.findAllById(userIds);
    }

    /**
     * Get a specific user with a given userID.
     *
     * @param userId
     * @return A registered user with a given userID. If it is already existed, throw a UserNotFoundException.
     * @throws UserNotFoundException
     */
    @Override
    public User getUser(UUID userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                             .orElseThrow(() -> new UserNotFoundException(userId));
    }

    /**
     * Get a specific user with a given email.
     *
     * @param email
     * @return A registered user with a given email. If it is already existed, throw a UserNotFoundException.
     * @throws UserNotFoundException
     */
    @Override
    public User getUser(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                             .orElseThrow(() -> new UserNotFoundException(email));
    }

    /**
     * Get list of users with a given Business ID.
     *
     * @param businessId
     * @return List of users with the given business ID.
     * @throws UserNotFoundException
     */
    @Override
    public List<User> getUsersByBusiness(UUID businessId) throws BusinessNotFoundException {
        Business business = businessService.getBusiness(businessId);
        return userRepository.findByBusiness(business);
    }

    /**
     * Add a new User.
     *
     * @param user
     * @return A newly registered user. If it is already existed, throw a UserNotFoundException.
     * @throws UserAlreadyRegisteredException
     */
    @Override
    public User addUser(User user) throws UserAlreadyRegisteredException {
        String email = user.getEmail();

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyRegisteredException(email);
        }

        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
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