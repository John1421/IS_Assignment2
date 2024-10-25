package com.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.server.model.UserMedia;
import com.server.service.UserMediaService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user-media")
public class UserMediaController {

    @Autowired
    private UserMediaService userMediaService;

    // Create a new user-media relationship
    @PostMapping
    public Mono<UserMedia> createUserMediaRelationship(@RequestParam long userId, @RequestParam long mediaId) {
        return userMediaService.createUserMediaRelationship(userId, mediaId);
    }

    // Get all media for a specific user
    @GetMapping("/user/{userId}")
    public Flux<UserMedia> getMediaByUserId(@PathVariable("userId") long userId) {
        return userMediaService.getMediaByUserId(userId);
    }

    // Get all users for a specific media
    @GetMapping("/media/{mediaId}")
    public Flux<UserMedia> getUsersByMediaId(@PathVariable("mediaId") long mediaId) {
        return userMediaService.getUsersByMediaId(mediaId);
    }

    // Delete user-media relationship by ID
    @DeleteMapping("/{id}")
    public Mono<Void> deleteUserMediaRelationship(@PathVariable("id") long id) {
        return userMediaService.deleteUserMediaRelationship(id);
    }
}
