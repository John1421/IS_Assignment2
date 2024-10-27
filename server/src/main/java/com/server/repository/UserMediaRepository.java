package com.server.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.server.model.UserMedia;

public interface UserMediaRepository extends ReactiveCrudRepository<UserMedia, Long> {
}
