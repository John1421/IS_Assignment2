package com.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.model.Media;
import com.server.repository.MediaRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;

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

    public Mono<Void> deleteMedia(long id) {
        return mediaRepository.deleteById(id);
    }
}
