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
                .doOnError(error -> log.error("Error fetching all media: {}", error))
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

    // Create new media
    @PostMapping
    private Mono<Media> createMedia(@RequestBody Media media) {
        log.info("Received request to create new media: {}", media);
        return mediaService.createMedia(media)
                .doOnSuccess(createdMedia -> log.info("Successfully created media: {}", createdMedia))
                .doOnError(e -> log.error("Error creating media: {}", e.getMessage()));
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
                .doOnSuccess(unused -> log.info("Successfully deleted media with ID {}", id))
                .doOnError(e -> log.error("Error deleting media with ID {}: {}", id, e.getMessage()));
    }
}
