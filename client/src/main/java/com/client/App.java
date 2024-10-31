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
                .doOnError(error -> writer.println("Error!!!"))
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
            
        } catch (IOException e) {
            System.err.println("Failed to write to output file: " + e.getMessage());
        }
    }
}
