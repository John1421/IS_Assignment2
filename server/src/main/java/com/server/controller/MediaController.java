package com.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.server.model.Media;
import com.server.model.Relationship;
import com.server.service.MediaService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/media")
@Slf4j
public class MediaController {

    @Autowired
    private MediaService mediaService;

    // Get all media
    @GetMapping
    public Flux<Media> getAllMedia() {
        log.info("Received request to fetch all media.");
        return mediaService.getAllMedia()
                .doOnNext(media -> log.debug("Fetched media item: {}", media))
                .doOnError(e -> log.error("Error fetching all media: {}", e.getMessage()))
                .doOnComplete(() -> log.info("Completed fetching all media."));
    }

    // Get a specific media by ID
    @GetMapping("/{id}")
    public Mono<Media> getMediaById(@PathVariable("id") long id) {
        log.info("Received request to fetch media with ID: {}", id);
        return mediaService.getMediaById(id)
                .doOnNext(media -> log.info("Found media: {}", media))
                .doOnError(e -> log.error("Error fetching media with ID {}: {}", id, e.getMessage()));
    }

    @GetMapping("/{id}/users")
    public Flux<Long> getUsersByMediaId(@PathVariable("id") Long id) {
        log.info("Received request to fetch users subscribed to media with ID: {}", id);
        return mediaService.getUsersByMediaId(id)
                .doOnNext(userId -> log.debug("Fetched user with id: {}", userId))
                .doOnError(
                        e -> log.error("Error fetching users subscribed to media with ID {}: {}", id, e.getMessage()))
                .doOnComplete(() -> log.info("Completed fetching users subscribed to media with ID: {}", id));
    }

    // Create new media
    @PostMapping
    private Mono<Media> createMedia(@RequestBody Media media) {
        log.info("Received request to create new media: {}", media);
        return mediaService.createMedia(media)
                .doOnSuccess(createdMedia -> log.info("Successfully created media: {}", createdMedia))
                .doOnError(e -> log.error("Error creating media: {}", e.getMessage()));
    }

    @PostMapping("/users")
    public Mono<Relationship> createRelationship(@RequestBody Relationship relationship) {
        log.info("Received request to create new relationship: {}", relationship);
        return mediaService.createRelationship(relationship)
                .doOnSuccess(
                        createdRelationship -> log.info("Successfully created relationship: {}", createdRelationship))
                .doOnError(e -> log.error("Error creating relationship: {}", e.getMessage()));
    }

    // Update existing media by ID
    @PutMapping("/{id}")
    private Mono<Media> updateMedia(@PathVariable("id") long id, @RequestBody Media updatedMedia) {
        log.info("Received request to update media with ID {}: {}", id, updatedMedia);
        return mediaService.updateMedia(updatedMedia)
                .doOnSuccess(updated -> log.info("Successfully updated media with ID {}: {}", id, updated))
                .doOnError(e -> log.error("Error updating media with ID {}: {}", id, e.getMessage()));
    }

    // Delete media by ID
    @DeleteMapping("/{id}")
    private Mono<Media> deleteMedia(@PathVariable("id") long id) {
        log.info("Received request to delete media with ID: {}", id);
        return mediaService.deleteMedia(id)
                .doOnSuccess(deletedMedia -> log.info("Successfully deleted media: {}", deletedMedia))
                .doOnError(e -> log.error("Error deleting media with ID {}: {}", id, e.getMessage()));
    }

    @DeleteMapping("/{mediaId}/{userId}")
    public Mono<Relationship> deleteRelationship(@PathVariable("mediaId") Long mediaId,
            @PathVariable("userId") Long userId) {
        log.info("Received request to delete relationship: {}", new Relationship(mediaId, userId));
        return mediaService.deleteRelationship(mediaId, userId)
                .doOnSuccess(
                        deletedRelationship -> log.info("Successfully deleted relationship: {}", deletedRelationship))
                .doOnError(e -> log.error("Error deleting relationship {}: {}", new Relationship(mediaId, userId),
                        e.getMessage()));
    }

}
