package com.server.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserMediaRepository extends R2dbcRepository<UserMedia, Long> {
}
