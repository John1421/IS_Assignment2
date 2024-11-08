package com.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.model.Media;
import com.server.model.Relationship;
import com.server.repository.MediaRepository;
import com.server.repository.RelationshipRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    public Mono<Media> createMedia(Media media) {
        return mediaRepository.save(media);
    }

    public Flux<Media> getAllMedia() {
        return mediaRepository.findAll();
    }

    public Mono<Media> getMediaById(long id) {
        return mediaRepository.findById(id);
    }

    public Mono<Media> updateMedia(Media media) {
        return mediaRepository.findById(media.getId())
                .flatMap(existingMedia -> mediaRepository.save(media));
    }

    public Mono<Media> deleteMedia(long id) {
        return getMediaById(id)
                .doOnSuccess(m -> {
                    mediaRepository.deleteById(id);
                });
    }

    public Flux<Long> getUsersByMediaId(long id) {
        return relationshipRepository.findByMediaId(id).map(r -> r.getUserId());
    }

    public Mono<Relationship> createRelationship(Relationship relationship) {
        return relationshipRepository.save(relationship);
    }

    public Mono<Relationship> deleteRelationship(long mediaId, long userId) {
        return relationshipRepository.findAllById(Flux.just(mediaId))
                .filter(rel -> rel.getUserId() == userId)
                .next()
                .flatMap(rel -> {
                    relationshipRepository.delete(rel);
                    return Mono.just(rel);
                });
    }

}
