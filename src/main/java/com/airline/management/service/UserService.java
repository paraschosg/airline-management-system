package com.airline.management.service;

import com.airline.management.dto.RegisterRequest;
import com.airline.management.dto.UpdateUserRequest;
import com.airline.management.model.Role;
import com.airline.management.model.User;
import com.airline.management.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(request.getRole());
        user.setAfm(request.getAfm());
        user.setAddress(request.getAddress());
        user.setEmployeeCode(request.getEmployeeCode());
        user.setIdentityNumber(request.getIdentityNumber());

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

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User findByAfm(String afm){
        return userRepository.findByAfm(afm).orElseThrow(()->new RuntimeException("User not found"));
    }

    public User findByIdentityNumber(String identityNumber){
        return userRepository.findByIdentityNumber(identityNumber).orElseThrow(()->new RuntimeException("User not found"));
    }

    public User updateUser(Long id, UpdateUserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAddress(request.getAddress());
        user.setAfm(request.getAfm());
        user.setEmployeeCode(request.getEmployeeCode());
        user.setIdentityNumber(request.getIdentityNumber());
        user.setRole(request.getRole());
        user.setActive(request.isActive());

        return userRepository.save(user);
    }

    public void deleteUser(Long id){

        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);
    }
}