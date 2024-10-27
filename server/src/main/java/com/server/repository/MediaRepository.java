package com.server.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MediaRepository extends ReactiveCrudRepository<Media, Long> {
}
