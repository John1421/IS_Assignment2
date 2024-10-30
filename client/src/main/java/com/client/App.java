package com.client;

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
        webClient.get()
                .uri("/media")
                .retrieve()
                .bodyToFlux(String.class)
                .doOnNext(System.out::println)
                .doOnError(error -> System.err.println("Error!!!"))
                .subscribe();

        // REQ 4
        webClient.get()
                .uri("/media")
                .retrieve()
                .bodyToFlux(Media.class)
                .collectList()
                .flatMap(m -> Mono.just(m.size()))
                .subscribe(System.out::println);

        // REQ 5
        webClient.get()
                .uri("/media")
                .retrieve()
                .bodyToFlux(Media.class)
                .filter(m -> m.getReleaseDate().isAfter(LocalDate.of(1980, 1, 1)) &&
                        m.getReleaseDate().isBefore(LocalDate.of(1989, 12, 31)))
                .sort((m1, m2) -> Double.compare(m1.getAverageRating(), m2.getAverageRating()))
                .subscribe(System.out::println, error -> System.err.println("Error: " + error.getMessage()));

    }
}
