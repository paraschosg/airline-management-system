package com.airline.management.controller;

import com.airline.management.model.User;
import com.airline.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody LoginRequest request) {
        User user = userService.findByUsername(request.getUsername());

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        return user;
    }
}