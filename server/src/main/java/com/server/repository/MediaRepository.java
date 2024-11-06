package com.server.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.server.model.Media;

public interface MediaRepository extends ReactiveCrudRepository<Media, Long> {
}
