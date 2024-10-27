package com.server.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends R2dbcRepository<User, Long> {
}
