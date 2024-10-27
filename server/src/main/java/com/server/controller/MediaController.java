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

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/media")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    // Get all media
    @GetMapping
    public Flux<Media> getAllMedia() {
        return mediaService.getAllMedia();
    }

    // Get a specific media by ID
    @GetMapping("/{id}")
    public Mono<Media> getMediaById(@PathVariable("id") long id) {
        return mediaService.getMediaById(id);
    }

    // Create new media
    @PostMapping
    private Mono<Media> createMedia(@RequestBody Media media) {
        return mediaService.createMedia(media);
    }

    // Update existing media by ID
    @PutMapping("/{id}")
    private Mono<Media> updateMedia(@PathVariable("id") long id, @RequestBody Media updatedMedia) {
        return mediaService.updateMedia(updatedMedia);
    }

    // Delete media by ID
    @DeleteMapping("/{id}")
    private Mono<Media> deleteMedia(@PathVariable("id") long id) {
        Mono<Media> media = mediaService.getMediaById(id);
        mediaService.deleteMedia(id);
        Mono<Media> media2 = mediaService.getMediaById(id);
        System.out.println(media + " | " + media2);
        return media;
    }
}
