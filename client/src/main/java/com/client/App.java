package com.client;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@SpringBootApplication
public class App implements CommandLineRunner {

    private final WebClient webClient;

    @Autowired
    public App(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://host.docker.internal:8080").build();
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) {
        // Write output to a file
        try (PrintWriter writer = new PrintWriter(new FileWriter("output.txt"))) {
            
            writer.println("---------------------REQ 1------------------------");
            webClient.get()
                .uri("/media")
                .retrieve()
                .bodyToFlux(Media.class)
                .doOnNext(media -> writer.println("Title: " + media.getTitle() + ", Release Date: " + media.getReleaseDate()))
                .doOnError(error -> writer.println("Error: " + error.toString()))
                .blockLast();
            
            writer.println("---------------------REQ 2------------------------");

            int totalMediaCount = webClient.get()
                .uri("/media")
                .retrieve()
                .bodyToFlux(Media.class)
                .collectList()
                .flatMap(m -> Mono.just(m.size()))
                .block();
            writer.println("Total Media Count: " + totalMediaCount);
            
            writer.println("---------------------REQ 3------------------------");

            int reallyGoodMediaCount = webClient.get()
                .uri("/media")
                .retrieve()
                .bodyToFlux(Media.class)
                .filter(m -> m.getAverageRating() > 8)
                .collectList()
                .flatMap(m -> Mono.just(m.size()))
                .block();
            writer.println("Total Really Good Media Count (Rating > 8): " + reallyGoodMediaCount);

            writer.println("---------------------REQ 4------------------------");

            int mediaCount = webClient.get()
                .uri("/media")
                .retrieve()
                .bodyToFlux(Media.class)
                .collectList()
                .flatMap(m -> Mono.just(m.size()))
                .block();
            writer.println("Media Count: " + mediaCount);

            writer.println("---------------------REQ 5------------------------");

            webClient.get()
                .uri("/media")
                .retrieve()
                .bodyToFlux(Media.class)
                .filter(m -> m.getReleaseDate().isAfter(LocalDate.of(1980, 1, 1)) &&
                        m.getReleaseDate().isBefore(LocalDate.of(1989, 12, 31)))
                .sort((m1, m2) -> Double.compare(m1.getAverageRating(), m2.getAverageRating()))
                .doOnNext(writer::println)
                .doOnError(error -> writer.println("Error: " + error.getMessage()))
                .blockLast();
            
            writer.println("---------------------REQ 8------------------------");

            webClient.get()
                .uri("/media")
                .retrieve()
                .bodyToFlux(Media.class)
                .flatMap(media -> webClient.get()
                        .uri("/media/{id}/users", media.getId())
                        .retrieve()
                        .bodyToFlux(Long.class)
                        .collectList()
                        .map(users -> new MediaUserCount(media.getId(), users.size())))
                .collectList()
                .flatMap(mediaUserCounts -> {
                    int totalMediaItems = mediaUserCounts.size();
                    int totalUserCount = mediaUserCounts.stream().mapToInt(MediaUserCount::getUserCount).sum();
                    double averageUsersPerMedia = totalMediaItems == 0 ? 0 : (double) totalUserCount / totalMediaItems;

                    writer.println("Average number of users per media item: " + averageUsersPerMedia);
                    return Mono.just(averageUsersPerMedia);
                })
                .block();

        } catch (IOException e) {
            System.err.println("Failed to write to output file: " + e.getMessage());
        }
    }

    // Helper class to keep track of media and user count
    private static class MediaUserCount {
        private long mediaId;
        private int userCount;

        public MediaUserCount(long mediaId, int userCount) {
            this.mediaId = mediaId;
            this.userCount = userCount;
        }

        public int getUserCount() {
            return userCount;
        }
    }
}
