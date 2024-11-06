package com.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.model.User;
import com.server.repository.RatingRepository;
import com.server.repository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingRepository ratingRepository;

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

    public Mono<User> deleteUser(long id) {
        return getUserById(id)
                .doOnSuccess(u -> {
                    userRepository.deleteById(id);
                });
    }

    public Flux<Long> getMediasByUserId(long id) {
        return ratingRepository.findByMediaId(id).map(r -> r.getUserId());
    }
}
