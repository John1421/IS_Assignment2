package com.server.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.server.model.User;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
}
