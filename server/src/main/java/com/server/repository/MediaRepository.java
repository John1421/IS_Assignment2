package com.server.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.server.model.Media;

public interface MediaRepository extends ReactiveCrudRepository<Media, Long> {

    // // Custom query to find user IDs by media ID
    // @Query("SELECT user_id FROM ratings WHERE media_id = ?1")
    // Flux<Long> findUserIdsByMediaId(long mediaId);
}
