package com.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.model.User;
import com.server.repository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Mono<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    public Mono<User> updateUser(User user) {
        return userRepository.findById(user.getId())
                .flatMap(existingUser -> userRepository.save(user));
    }

    public Mono<Void> deleteUser(long id) {
        return userRepository.deleteById(id);
    }
}
