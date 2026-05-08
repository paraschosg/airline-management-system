package com.airline.management.controller;

import com.airline.management.dto.UpdateUserRequest;
import com.airline.management.model.User;
import com.airline.management.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("afm/{afm}")
    public User getByAfm(@PathVariable String afm) {
        return userService.findByAfm(afm);
    }

    @GetMapping("/identity/{identityNumber}")
    public User getByIdentityNumber(@PathVariable String identityNumber) {
        return userService.findByIdentityNumber(identityNumber);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);
        return "User has been deleted";
    }
}