package com.airline.management.service;

import com.airline.management.model.Role;
import com.airline.management.model.User;
import com.airline.management.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {

        // username unique
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (user.getUsername() == null || user.getPassword() == null) {
            throw new RuntimeException("Missing fields");
        }

        user.setActive(true);

        if (user.getRole() == null) {
            user.setRole(Role.CUSTOMER);
        }

        return userRepository.save(user);
    }

    public User login(String username, String password) {

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Wrong password");
        }

        return user;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }
}