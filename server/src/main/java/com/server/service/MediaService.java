package com.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.model.Media;
import com.server.model.Rating;
import com.server.repository.MediaRepository;
import com.server.repository.RatingRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private RatingRepository ratingRepository;

    public Mono<Media> createMedia(Media media) {
        return mediaRepository.save(media);
    }

    public Flux<Media> getAllMedia() {
        return mediaRepository.findAll();
    }

    /* public Mono<Media> getMediaById(long id) {
        return mediaRepository.findById(id);
    } */

    public Mono<Media> getMediaById(long id) {
        Mono<Media> mediaMono = mediaRepository.findById(id);
        Flux<Rating> ratingsFlux = ratingRepository.findByMediaId(id);
        Flux<Double> ratingsValuesFlux = ratingsFlux.map(Rating::getRating);
        Flux<Long> userIdsFlux = ratingsFlux.map(Rating::getUserId);

        return mediaMono.zipWith(ratingsValuesFlux.collectList(), (media, ratings) -> {
            media.setRatings(ratings);
            return media;
        }).zipWith(userIdsFlux.collectList(), (media, userIds) -> {
            media.setUserIds(userIds);
            return media;
        });
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

    // public Flux<Long> getMediaRelationships(long id) {
    // return mediaRepository
    // .findById(id)
    // .flatMap(media -> {
    // // return Flux.just(media.getUsers())
    // });
    // }

}
