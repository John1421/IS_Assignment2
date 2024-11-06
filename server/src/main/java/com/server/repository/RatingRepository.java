package com.server.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.server.model.Rating;

import reactor.core.publisher.Flux;

public interface RatingRepository extends ReactiveCrudRepository<Rating, Long> {

    Flux<Rating> findByMediaId(Long mediaId);

    Flux<Rating> findByUserId(Long userId);

}
