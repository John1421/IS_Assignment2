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

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    // Get all users
    @GetMapping
    public Flux<User> getAllUsers() {
        log.info("Received request to fetch all users.");
        return userService.getAllUsers()
                .doOnNext(user -> log.debug("Fetched user: {}", user))
                .doOnError(error -> log.error("Error fetching all users: {}", error))
                .doOnComplete(() -> log.info("Completed fetching all users."));
    }

    // Get a specific user by ID
    @GetMapping("/{id}")
    public Mono<User> getUserById(@PathVariable("id") long id) {
        log.info("Received request to fetch user with ID: {}", id);
        return userService.getUserById(id)
                .doOnNext(user -> log.info("Found user: {}", user))
                .doOnError(e -> log.error("Error fetching user with ID {}: {}", id, e.getMessage()));
    }

    // Create new user
    @PostMapping
    public Mono<User> createUser(@RequestBody User user) {
        log.info("Received request to create new user: {}", user);
        return userService.createUser(user)
                .doOnSuccess(createdUser -> log.info("Successfully created user: {}", createdUser))
                .doOnError(e -> log.error("Error creating user: {}", e.getMessage()));
    }

    // Update existing user by ID
    @PutMapping("/{id}")
    public Mono<User> updateUser(@PathVariable("id") long id, @RequestBody User updatedUser) {
        log.info("Received request to update media with ID {}: {}", id, updatedUser);
        return userService.updateUser(updatedUser)
                .doOnSuccess(updated -> log.info("Successfully updated user with ID {}: {}", id, updated))
                .doOnError(e -> log.error("Error updating user with ID {}: {}", id, e.getMessage()));
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public Mono<User> deleteUser(@PathVariable("id") long id) {
        log.info("Received request to delete user with ID: {}", id);
        return userService.deleteUser(id)
                .doOnSuccess(deletedUser -> log.info("Successfully deleted user: {}", deletedUser))
                .doOnError(e -> log.error("Error deleting user with ID {}: {}", id, e.getMessage()));
    }
}
