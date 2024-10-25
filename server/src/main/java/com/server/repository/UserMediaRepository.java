package com.server.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import com.server.model.UserMedia;
import reactor.core.publisher.Flux;

public interface UserMediaRepository extends R2dbcRepository<UserMedia, Long> {

    // Custom method to get all media by user ID
    Flux<UserMedia> findByUserId(long userId);

    // Custom method to get all users by media ID
    Flux<UserMedia> findByMediaId(long mediaId);
}
