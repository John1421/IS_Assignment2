package com.server.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.server.model.Relationship;

public interface RelationshipRepository extends ReactiveCrudRepository<Relationship, Long> {
}
