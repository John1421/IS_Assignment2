package com.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.model.User;
import com.server.service.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Get all users
    @GetMapping
    public Flux<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get a specific user by ID
    @GetMapping("/{id}")
    public Mono<User> getUserById(@PathVariable("id") long id) {
        return userService.getUserById(id);
    }

    // Create new user
    @PostMapping
    public Mono<User> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // Update existing user by ID
    @PutMapping("/{id}")
    public Mono<User> updateUser(@PathVariable("id") long id, @RequestBody User updatedUser) {
        updatedUser.setId(id);
        return userService.updateUser(updatedUser);
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable("id") long id) {
        return userService.deleteUser(id);
    }
}
