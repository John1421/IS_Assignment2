package com.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired
    private WebClient webClient;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        webClient.get()
                .uri("/media")
                .retrieve()
                .bodyToFlux(String.class)
                .subscribe(System.out::println);
    }
}
