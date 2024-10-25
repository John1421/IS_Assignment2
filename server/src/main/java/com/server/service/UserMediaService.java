package com.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.model.UserMedia;
import com.server.repository.UserMediaRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserMediaService {

    @Autowired
    private UserMediaRepository userMediaRepository;

    public Mono<UserMedia> createUserMediaRelationship(long userId, long mediaId) {
        return userMediaRepository.save(new UserMedia(0, userId, mediaId));
    }

    public Flux<UserMedia> getMediaByUserId(long userId) {
        return userMediaRepository.findByUserId(userId);
    }

    public Flux<UserMedia> getUsersByMediaId(long mediaId) {
        return userMediaRepository.findByMediaId(mediaId);
    }

    public Mono<Void> deleteUserMediaRelationship(long id) {
        return userMediaRepository.deleteById(id);
    }
}
