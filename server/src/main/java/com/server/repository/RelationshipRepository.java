package com.server.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.server.model.Relationship;

import reactor.core.publisher.Flux;

public interface RelationshipRepository extends ReactiveCrudRepository<Relationship, Long> {
    Flux<Relationship> findByUserId(Long userId);
    Flux<Relationship> findByMediaId(Long mediaId);
}
